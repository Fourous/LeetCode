/*
 * Copyright 2011 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lmax.disruptor;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Convenience class for handling the batching semantics of consuming entries from a {@link RingBuffer}
 * and delegating the available events to an {@link EventHandler}.
 * <p>
 * If the {@link EventHandler} also implements {@link LifecycleAware} it will be notified just after the thread
 * is started and just before the thread is shutdown.
 * 1. BatchEventProcessor内部会记录自己的序列、运行状态。
 * 2. BatchEventProcessor需要外部提供数据提供者(其实就是队列-RingBuffer)、序列栅栏、异常处理器。
 * 3. BatchEventProcessor其实是将事件委托给内部的EventHandler来处理的
 *
 * @param <T> event implementation storing the data for sharing during exchange or parallel coordination of an event.
 */
public final class BatchEventProcessor<T> implements EventProcessor {
    private static final int IDLE = 0;
    private static final int HALTED = IDLE + 1;
    private static final int RUNNING = HALTED + 1;
    /**
     * 表示当前事件处理器的运行状态
     * 这里之前版本是用的AtomicBoolean存储的状态现在用Integer存储
     */
    private final AtomicInteger running = new AtomicInteger(IDLE);
    /**
     * 异常处理器
     */
    private ExceptionHandler<? super T> exceptionHandler = new FatalExceptionHandler();
    /**
     * 数据提供者(RingBuffer)
     */
    private final DataProvider<T> dataProvider;
    /**
     * 序列栅栏
     */
    private final SequenceBarrier sequenceBarrier;
    /**
     * 真正处理事件的回调接口
     */
    private final EventHandler<? super T> eventHandler;
    /**
     * 事件处理器使用的序列
     */
    private final Sequence sequence = new Sequence(Sequencer.INITIAL_CURSOR_VALUE);
    /**
     * 超时回调接口
     */
    private final TimeoutHandler timeoutHandler;
    private final BatchStartAware batchStartAware;

    /**
     * Construct a {@link EventProcessor} that will automatically track the progress by updating its sequence when
     * the {@link EventHandler#onEvent(Object, long, boolean)} method returns.
     *
     * @param dataProvider    to which events are published.
     * @param sequenceBarrier on which it is waiting.
     * @param eventHandler    is the delegate to which events are dispatched.
     */
    public BatchEventProcessor(
            final DataProvider<T> dataProvider,
            final SequenceBarrier sequenceBarrier,
            final EventHandler<? super T> eventHandler) {
        this.dataProvider = dataProvider;
        this.sequenceBarrier = sequenceBarrier;
        this.eventHandler = eventHandler;

        if (eventHandler instanceof SequenceReportingEventHandler) {
            ((SequenceReportingEventHandler<?>) eventHandler).setSequenceCallback(sequence);
        }

        batchStartAware =
                (eventHandler instanceof BatchStartAware) ? (BatchStartAware) eventHandler : null;
        timeoutHandler =
                (eventHandler instanceof TimeoutHandler) ? (TimeoutHandler) eventHandler : null;
    }

    @Override
    public Sequence getSequence() {
        return sequence;
    }

    @Override
    public void halt() {
        /**
         * 之前版本使用的直接设置位false
         * 现在使用的数字来标志
         */
        running.set(HALTED);
        // 通知序列栅栏
        sequenceBarrier.alert();
    }

    @Override
    public boolean isRunning() {
        return running.get() != IDLE;
    }

    /**
     * Set a new {@link ExceptionHandler} for handling exceptions propagated out of the {@link BatchEventProcessor}
     *
     * @param exceptionHandler to replace the existing exceptionHandler.
     */
    public void setExceptionHandler(final ExceptionHandler<? super T> exceptionHandler) {
        if (null == exceptionHandler) {
            throw new NullPointerException();
        }

        this.exceptionHandler = exceptionHandler;
    }

    /**
     * It is ok to have another thread rerun this method after a halt().
     *
     * @throws IllegalStateException if this object instance is already running in a thread
     */
    @Override
    public void run() {
        if (running.compareAndSet(IDLE, RUNNING)) {
            // 先清除序列栅栏的通知状态
            sequenceBarrier.clearAlert();
            // 如果eventHandler实现了LifecycleAware，这里会对其进行一个启动通知
            notifyStart();
            try {
                if (running.get() == RUNNING) {
                    processEvents();
                }
            } finally {
                notifyShutdown();
                running.set(IDLE);
            }
        } else {
            // This is a little bit of guess work.  The running state could of changed to HALTED by
            // this point.  However, Java does not have compareAndExchange which is the only way
            // to get it exactly correct.
            if (running.get() == RUNNING) {
                throw new IllegalStateException("Thread is already running");
            } else {
                earlyExit();
            }
        }
    }

    private void processEvents() {
        T event = null;
        // 获取要申请的序列
        long nextSequence = sequence.get() + 1L;

        while (true) {
            try {
                // 通过序列栅栏来等待可用的序列值
                final long availableSequence = sequenceBarrier.waitFor(nextSequence);
                if (batchStartAware != null) {
                    batchStartAware.onBatchStart(availableSequence - nextSequence + 1);
                }
                // 得到可用的序列值后，批量处理nextSequence到availableSequence之间的事件
                while (nextSequence <= availableSequence) {
                    // 获取事件
                    event = dataProvider.get(nextSequence);
                    // 将事件交给eventHandler处理
                    eventHandler.onEvent(event, nextSequence, nextSequence == availableSequence);
                    nextSequence++;
                }
                // 处理完毕后，设置当前处理完成的最后序列值
                sequence.set(availableSequence);
            } catch (final TimeoutException e) {
                // 如果发生超时，通知一下超时处理器(如果eventHandler同时实现了timeoutHandler，会将其设置为当前的超时处理器
                notifyTimeout(sequence.get());
            } catch (final AlertException ex) {
                // 如果捕获了序列栅栏变更通知，并且当前事件处理器停止了，那么退出主循环
                if (running.get() != RUNNING) {
                    break;
                }
            } catch (final Throwable ex) {
                // 其他的异常都交给异常处理器进行处理
                exceptionHandler.handleEventException(ex, nextSequence, event);
                // 处理异常后仍然会设置当前处理的最后的序列值，然后继续处理其他事件
                sequence.set(nextSequence);
                nextSequence++;
            }
        }
    }

    private void earlyExit() {
        notifyStart();
        notifyShutdown();
    }

    private void notifyTimeout(final long availableSequence) {
        try {
            if (timeoutHandler != null) {
                timeoutHandler.onTimeout(availableSequence);
            }
        } catch (Throwable e) {
            exceptionHandler.handleEventException(e, availableSequence, null);
        }
    }

    /**
     * Notifies the EventHandler when this processor is starting up
     */
    private void notifyStart() {
        if (eventHandler instanceof LifecycleAware) {
            try {
                ((LifecycleAware) eventHandler).onStart();
            } catch (final Throwable ex) {
                exceptionHandler.handleOnStartException(ex);
            }
        }
    }

    /**
     * Notifies the EventHandler immediately prior to this processor shutting down
     */
    private void notifyShutdown() {
        if (eventHandler instanceof LifecycleAware) {
            try {
                ((LifecycleAware) eventHandler).onShutdown();
            } catch (final Throwable ex) {
                exceptionHandler.handleOnShutdownException(ex);
            }
        }
    }
}