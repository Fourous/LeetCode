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

import java.util.concurrent.locks.LockSupport;

import com.lmax.disruptor.util.Util;

abstract class SingleProducerSequencerPad extends AbstractSequencer {
    protected long p1, p2, p3, p4, p5, p6, p7;

    SingleProducerSequencerPad(int bufferSize, WaitStrategy waitStrategy) {
        super(bufferSize, waitStrategy);
    }
}

abstract class SingleProducerSequencerFields extends SingleProducerSequencerPad {
    SingleProducerSequencerFields(int bufferSize, WaitStrategy waitStrategy) {
        super(bufferSize, waitStrategy);
    }

    /**
     * Set to -1 as sequence starting point
     */
    long nextValue = Sequence.INITIAL_VALUE;
    long cachedValue = Sequence.INITIAL_VALUE;
}

/**
 * <p>Coordinator for claiming sequences for access to a data structure while tracking dependent {@link Sequence}s.
 * Not safe for use from multiple threads as it does not implement any barriers.</p>
 *
 * <p>* Note on {@link Sequencer#getCursor()}:  With this sequencer the cursor value is updated after the call
 * to {@link Sequencer#publish(long)} is made.</p>
 */

public final class SingleProducerSequencer extends SingleProducerSequencerFields {
    protected long p1, p2, p3, p4, p5, p6, p7;

    /**
     * Construct a Sequencer with the selected wait strategy and buffer size.
     *
     * @param bufferSize   the size of the buffer that this will sequence over.
     * @param waitStrategy for those waiting on sequences.
     */
    public SingleProducerSequencer(int bufferSize, WaitStrategy waitStrategy) {
        super(bufferSize, waitStrategy);
    }

    /**
     * @see Sequencer#hasAvailableCapacity(int)
     */
    @Override
    public boolean hasAvailableCapacity(int requiredCapacity) {
        return hasAvailableCapacity(requiredCapacity, false);
    }

    /**
     * 当前序列的nextValue + requiredCapacity是事件发布者要申请的序列值。
     * 当前序列的cachedValue记录的是之前事件处理者申请的序列值。
     * 想一下一个环形队列，事件发布者在什么情况下才能申请一个序列呢？
     * 事件发布者当前的位置在事件处理者前面，并且不能从事件处理者后面追上事件处理者(因为是环形)，
     * 即 事件发布者要申请的序列值大于事件处理者之前的序列值 且 事件发布者要申请的序列值减去环的长度要小于事件处理者的序列值
     * 如果满足这个条件，即使不知道当前事件处理者的序列值，也能确保事件发布者可以申请给定的序列。
     * 如果不满足这个条件，就需要查看一下当前事件处理者的最小的序列值(因为可能有多个事件处理者)，
     * 如果当前要申请的序列值比当前事件处理者的最小序列值大了一圈(从后面追上了)，
     * 那就不能申请了(申请的话会覆盖没被消费的事件)，也就是说没有可用的空间(用来发布事件)了，
     * 也就是hasAvailableCapacity方法要表达的意思
     *
     * @param requiredCapacity
     * @param doStore
     * @return
     */
    private boolean hasAvailableCapacity(int requiredCapacity, boolean doStore) {
        long nextValue = this.nextValue;

        long wrapPoint = (nextValue + requiredCapacity) - bufferSize;
        long cachedGatingSequence = this.cachedValue;

        if (wrapPoint > cachedGatingSequence || cachedGatingSequence > nextValue) {
            if (doStore) {
                /**
                 * StoreLoad fence
                 * 这里是StoreLoad非常经典场景，因为CPU目前大多数都是Strong Memory Model类型，只是LL，SS，LS，但是不支持SL
                 * 例如常见的 x86,x64 架构的 CPU(例如 intel i3,i5,i7) 一般都属于这种类型，因为如果支持SL的CPU，是要耗费大量的性能的
                 * 所以其实我们主要关心的是怎么保证SL的顺序
                 *
                 */
                cursor.setVolatile(nextValue);
            }

            long minSequence = Util.getMinimumSequence(gatingSequences, nextValue);
            this.cachedValue = minSequence;

            if (wrapPoint > minSequence) {
                return false;
            }
        }

        return true;
    }

    /**
     * @see Sequencer#next()
     */
    @Override
    public long next() {
        return next(1);
    }

    /**
     * @see Sequencer#next(int)
     * next方法是真正申请序列的方法，里面的逻辑和hasAvailableCapacity一样，只是在不能申请序列的时候会阻塞等待一下，然后重试
     */
    @Override
    public long next(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be > 0");
        }

        long nextValue = this.nextValue;

        long nextSequence = nextValue + n;
        long wrapPoint = nextSequence - bufferSize;
        long cachedGatingSequence = this.cachedValue;

        if (wrapPoint > cachedGatingSequence || cachedGatingSequence > nextValue) {
            // StoreLoad fence
            cursor.setVolatile(nextValue);

            long minSequence;
            while (wrapPoint > (minSequence = Util.getMinimumSequence(gatingSequences, nextValue))) {
                // TODO: Use waitStrategy to spin?
                LockSupport.parkNanos(1L);
            }

            this.cachedValue = minSequence;
        }

        this.nextValue = nextSequence;

        return nextSequence;
    }

    /**
     * @see Sequencer#tryNext()
     */
    @Override
    public long tryNext() throws InsufficientCapacityException {
        return tryNext(1);
    }

    /**
     * @see Sequencer#tryNext(int)
     * trynext是next是非阻塞版本
     * 不能申请就直接抛异常
     */
    @Override
    public long tryNext(int n) throws InsufficientCapacityException {
        if (n < 1) {
            throw new IllegalArgumentException("n must be > 0");
        }

        if (!hasAvailableCapacity(n, true)) {
            throw InsufficientCapacityException.INSTANCE;
        }

        long nextSequence = this.nextValue += n;

        return nextSequence;
    }

    /**
     * @see Sequencer#remainingCapacity()
     * remainingCapacity方法就是环形队列的容量减去事件发布者与事件处理者的序列差
     */
    @Override
    public long remainingCapacity() {
        long nextValue = this.nextValue;

        long consumed = Util.getMinimumSequence(gatingSequences, nextValue);
        long produced = nextValue;
        return getBufferSize() - (produced - consumed);
    }

    /**
     * @see Sequencer#claim(long)
     */
    @Override
    public void claim(long sequence) {
        this.nextValue = sequence;
    }

    /**
     * @see Sequencer#publish(long)
     * 发布一个序列，会先设置内部游标值，然后唤醒等待的事件处理者
     */
    @Override
    public void publish(long sequence) {
        cursor.set(sequence);
        waitStrategy.signalAllWhenBlocking();
    }

    /**
     * @see Sequencer#publish(long, long)
     */
    @Override
    public void publish(long lo, long hi) {
        publish(hi);
    }

    /**
     * @see Sequencer#isAvailable(long)
     */
    @Override
    public boolean isAvailable(long sequence) {
        return sequence <= cursor.get();
    }

    @Override
    public long getHighestPublishedSequence(long lowerBound, long availableSequence) {
        return availableSequence;
    }
}
