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

import com.lmax.disruptor.util.Util;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * WorkerPool contains a pool of {@link WorkProcessor}s that will consume sequences so jobs can be farmed out across a pool of workers.
 * Each of the {@link WorkProcessor}s manage and calls a {@link WorkHandler} to process the events.
 * <p>
 * WorkerPool是Work模式下的事件处理器池，它起到了维护事件处理器生命周期、关联事件处理器与事件队列(RingBuffer)、
 * 提供工作序列(上面分析WorkProcessor时看到多个处理器需要使用统一的workSequence)的作用。
 *
 * @param <T> event to be processed by a pool of workers
 */
public final class WorkerPool<T> {

    // 运行状态标志
    private final AtomicBoolean started = new AtomicBoolean(false);
    // 工作序列
    private final Sequence workSequence = new Sequence(Sequencer.INITIAL_CURSOR_VALUE);
    // 事件队列
    private final RingBuffer<T> ringBuffer;
    // WorkProcessors are created to wrap each of the provided WorkHandlers
    // 事件处理器数组
    private final WorkProcessor<?>[] workProcessors;

    /**
     * Create a worker pool to enable an array of {@link WorkHandler}s to consume published sequences.
     * <p>
     * This option requires a pre-configured {@link RingBuffer} which must have {@link RingBuffer#addGatingSequences(Sequence...)}
     * called before the work pool is started.
     *
     * @param ringBuffer       of events to be consumed.
     * @param sequenceBarrier  on which the workers will depend.
     * @param exceptionHandler to callback when an error occurs which is not handled by the {@link WorkHandler}s.
     * @param workHandlers     to distribute the work load across.
     */
    @SafeVarargs
    public WorkerPool(
            final RingBuffer<T> ringBuffer,
            final SequenceBarrier sequenceBarrier,
            final ExceptionHandler<? super T> exceptionHandler,
            final WorkHandler<? super T>... workHandlers) {
        this.ringBuffer = ringBuffer;
        final int numWorkers = workHandlers.length;
        workProcessors = new WorkProcessor[numWorkers];

        for (int i = 0; i < numWorkers; i++) {
            workProcessors[i] = new WorkProcessor<>(
                    ringBuffer,
                    sequenceBarrier,
                    workHandlers[i],
                    exceptionHandler,
                    workSequence);
        }
    }

    /**
     * Construct a work pool with an internal {@link RingBuffer} for convenience.
     * <p>
     * This option does not require {@link RingBuffer#addGatingSequences(Sequence...)} to be called before the work pool is started.
     *
     * @param eventFactory     for filling the {@link RingBuffer}
     * @param exceptionHandler to callback when an error occurs which is not handled by the {@link WorkHandler}s.
     * @param workHandlers     to distribute the work load across.
     */
    @SafeVarargs
    public WorkerPool(
            final EventFactory<T> eventFactory,
            final ExceptionHandler<? super T> exceptionHandler,
            final WorkHandler<? super T>... workHandlers) {
        ringBuffer = RingBuffer.createMultiProducer(eventFactory, 1024, new BlockingWaitStrategy());
        final SequenceBarrier barrier = ringBuffer.newBarrier();
        final int numWorkers = workHandlers.length;
        workProcessors = new WorkProcessor[numWorkers];

        for (int i = 0; i < numWorkers; i++) {
            workProcessors[i] = new WorkProcessor<>(
                    ringBuffer,
                    barrier,
                    workHandlers[i],
                    exceptionHandler,
                    workSequence);
        }

        ringBuffer.addGatingSequences(getWorkerSequences());
    }

    /**
     * Get an array of {@link Sequence}s representing the progress of the workers.
     *
     * @return an array of {@link Sequence}s representing the progress of the workers.
     */
    public Sequence[] getWorkerSequences() {
        final Sequence[] sequences = new Sequence[workProcessors.length + 1];
        for (int i = 0, size = workProcessors.length; i < size; i++) {
            sequences[i] = workProcessors[i].getSequence();
        }
        sequences[sequences.length - 1] = workSequence;

        return sequences;
    }

    /**
     * Start the worker pool processing events in sequence.
     *
     * @param executor providing threads for running the workers.
     * @return the {@link RingBuffer} used for the work queue.
     * @throws IllegalStateException if the pool has already been started and not halted yet
     */
    public RingBuffer<T> start(final Executor executor) {
        if (!started.compareAndSet(false, true)) {
            throw new IllegalStateException("WorkerPool has already been started and cannot be restarted until halted.");
        }

        final long cursor = ringBuffer.getCursor();
        workSequence.set(cursor);

        for (WorkProcessor<?> processor : workProcessors) {
            processor.getSequence().set(cursor);
            executor.execute(processor);
        }

        return ringBuffer;
    }

    /**
     * Wait for the {@link RingBuffer} to drain of published events then halt the workers.
     * drainAndHalt方法会将RingBuffer中所有的事件取出，执行完毕后，然后停止当前WorkerPool
     */
    public void drainAndHalt() {
        Sequence[] workerSequences = getWorkerSequences();
        while (ringBuffer.getCursor() > Util.getMinimumSequence(workerSequences)) {
            Thread.yield();
        }

        for (WorkProcessor<?> processor : workProcessors) {
            processor.halt();
        }

        started.set(false);
    }

    /**
     * Halt all workers immediately at the end of their current cycle.
     * 直接全部关闭，是不会管任务是否结束未结束的
     * 所以可能会丢失任务状态
     */
    public void halt() {
        for (WorkProcessor<?> processor : workProcessors) {
            processor.halt();
        }

        started.set(false);
    }

    /**
     * 直接返回状态
     *
     * @return
     */
    public boolean isRunning() {
        return started.get();
    }
}
