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

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.lmax.disruptor.util.ThreadHints;

/**
 * Blocking strategy that uses a lock and condition variable for {@link EventProcessor}s waiting on a barrier.
 * <p>
 * 默认等待策略
 * 阻塞等待
 * 当要求节省CPU资源，而不要求高吞吐量和低延迟的时候使用这个策略
 * This strategy can be used when throughput and low-latency are not as important as CPU resource.
 */
public final class BlockingWaitStrategy implements WaitStrategy {

    // 可重入锁
    private final Lock lock = new ReentrantLock();
    /**
     * 目前来看这里可以优化一下，在这个场景下，用synchronized 在JDK8下要好点
     * 对于ReentrantLock condition通知模型自由度大
     * 1、一个Lock里面可以创建多个Condition实例，实现多路通知
     * 2、notify()方法进行通知时，被通知的线程时Java虚拟机随机选择的，但是ReentrantLock结合Condition可以实现有选择性地通知，这是非常重要的
     */
    private final Condition processorNotifyCondition = lock.newCondition();

    @Override
    public long waitFor(long sequence, Sequence cursorSequence, Sequence dependentSequence, SequenceBarrier barrier)
            throws AlertException, InterruptedException {
        long availableSequence;
        if (cursorSequence.get() < sequence) {
            // 如果RingBuffer上当前可用的序列值小于要申请的序列值
            lock.lock();
            try {
                // 加锁后再检测
                while (cursorSequence.get() < sequence) {
                    // 检查序列栅栏状态(事件处理器是否被关闭)
                    barrier.checkAlert();
                    // 当前线程在processorNotifyCondition条件上等待
                    processorNotifyCondition.await();
                }
            } finally {
                lock.unlock();
            }
        }
        // 再次检测，避免事件处理器关闭的情况
        while ((availableSequence = dependentSequence.get()) < sequence) {
            barrier.checkAlert();
            ThreadHints.onSpinWait();
        }

        return availableSequence;
    }

    @Override
    public void signalAllWhenBlocking() {
        lock.lock();
        try {
            // 唤醒在processorNotifyCondition条件上等待的处理事件线程
            processorNotifyCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "BlockingWaitStrategy{" +
                "processorNotifyCondition=" + processorNotifyCondition +
                '}';
    }
}
