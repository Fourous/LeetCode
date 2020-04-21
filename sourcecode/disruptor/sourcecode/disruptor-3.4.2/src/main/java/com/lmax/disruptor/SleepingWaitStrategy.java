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

/**
 * Sleeping strategy that initially spins, then uses a Thread.yield(), and
 * eventually sleep (<code>LockSupport.parkNanos(n)</code>) for the minimum
 * number of nanos the OS and JVM will allow while the
 * {@link com.lmax.disruptor.EventProcessor}s are waiting on a barrier.
 * <p>
 * This strategy is a good compromise between performance and CPU resource.
 * Latency spikes can occur after quiet periods.  It will also reduce the impact
 * on the producing thread as it will not need signal any conditional variables
 * to wake up the event handling thread.
 * <p>
 * SleepingWaitStrategy的实现方法是先自旋，不行再临时让出调度(yield)，不行再短暂的阻塞等待。
 * 对于既想取得高性能，由不想太浪费CPU资源的场景，这个策略是一种比较好的折中方案。使用这个方案可能会出现延迟波动
 */
public final class SleepingWaitStrategy implements WaitStrategy {
    private static final int DEFAULT_RETRIES = 200;
    private static final long DEFAULT_SLEEP = 100;
    // 尝试次数
    private final int retries;
    // 临时等待
    private final long sleepTimeNs;

    public SleepingWaitStrategy() {
        this(DEFAULT_RETRIES, DEFAULT_SLEEP);
    }

    public SleepingWaitStrategy(int retries) {
        this(retries, DEFAULT_SLEEP);
    }

    public SleepingWaitStrategy(int retries, long sleepTimeNs) {
        this.retries = retries;
        this.sleepTimeNs = sleepTimeNs;
    }

    @Override
    public long waitFor(
            final long sequence, Sequence cursor, final Sequence dependentSequence, final SequenceBarrier barrier)
            throws AlertException {
        long availableSequence;
        int counter = retries;
        /**
         * 自旋
         */
        while ((availableSequence = dependentSequence.get()) < sequence) {
            counter = applyWaitMethod(barrier, counter);
        }

        return availableSequence;
    }

    @Override
    public void signalAllWhenBlocking() {
    }

    private int applyWaitMethod(final SequenceBarrier barrier, int counter)
            throws AlertException {
        barrier.checkAlert();
        // 如果大于100，先自旋
        if (counter > 100) {
            --counter;
        } else if (counter > 0) {
            // 如果<100，就临时让出调度
            --counter;
            Thread.yield();
        } else {
            // 阻塞100
            LockSupport.parkNanos(sleepTimeNs);
        }

        return counter;
    }
}
