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


import com.lmax.disruptor.util.ThreadHints;

/**
 * Busy Spin strategy that uses a busy spin loop for {@link com.lmax.disruptor.EventProcessor}s waiting on a barrier.
 * <p>
 * BusySpinWaitStrategy的实现方法是自旋等待。这种策略会利用CPU资源来避免系统调用带来的延迟抖动
 * 当线程可以绑定到指定CPU(核)的时候可以使用这个策略
 * <p>
 * Java语言的线程，从规范的角度来说是不强制要求任何具体的实现方式的。采用1:1、N:1、M:N模型都可以
 * 具体到我们平时常用的JVM实现，Oracle/Sun的HotSpot VM，它是用1:1模型来实现Java线程的，也就是说一个Java线程是直接通过一个OS线程来实现的
 * 中间并没有额外的间接结构。而且HotSpot VM自己也不干涉线程的调度，全权交给底下的OS去处理。所以如果OS想把某个线程调度到某个CPU/核上，它就自己弄了
 * 对于绑核的话，一般绑核有硬绑和软绑，软绑一般CPU在调度时候，会在调度时候，将经常运行的线程保持在一个核上
 * 如果一个线程保持运行，就要硬绑，可以借助taskset绑核
 * <p>
 * 这里也要注意，Java的线程不一定都是OS线程，只是对于HotSpot JVM是这样
 * This strategy will use CPU resource to avoid syscalls which can introduce latency jitter.  It is best
 * used when threads can be bound to specific CPU cores.
 */
public final class BusySpinWaitStrategy implements WaitStrategy {
    @Override
    public long waitFor(
            final long sequence, Sequence cursor, final Sequence dependentSequence, final SequenceBarrier barrier)
            throws AlertException, InterruptedException {
        long availableSequence;
        // 自旋
        while ((availableSequence = dependentSequence.get()) < sequence) {
            barrier.checkAlert();
            ThreadHints.onSpinWait();
        }

        return availableSequence;
    }

    @Override
    public void signalAllWhenBlocking() {
    }
}
