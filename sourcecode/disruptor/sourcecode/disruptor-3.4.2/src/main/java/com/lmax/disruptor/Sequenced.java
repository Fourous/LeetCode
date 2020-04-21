package com.lmax.disruptor;

public interface Sequenced {
    /**
     * The capacity of the data structure to hold entries.
     * 数据结构中事件槽的个数(就是RingBuffer的容量)
     *
     * @return the size of the RingBuffer.
     */
    int getBufferSize();

    /**
     * Has the buffer got capacity to allocate another sequence.  This is a concurrent
     * method so the response should only be taken as an indication of available capacity.
     * 判断是否还有给定的可用容量。
     *
     * @param requiredCapacity in the buffer
     * @return true if the buffer has the capacity to allocate the next sequence otherwise false.
     */
    boolean hasAvailableCapacity(int requiredCapacity);

    /**
     * Get the remaining capacity for this sequencer.
     * 获取剩余容量
     *
     * @return The number of slots remaining.
     */
    long remainingCapacity();

    /**
     * Claim the next event in sequence for publishing.
     * 申请下一个序列值，用来发布事件
     *
     * @return the claimed sequence value
     */
    long next();

    /**
     * Claim the next n events in sequence for publishing.  This is for batch event producing.  Using batch producing
     * requires a little care and some math.
     * <pre>
     * int n = 10;
     * long hi = sequencer.next(n);
     * long lo = hi - (n - 1);
     * for (long sequence = lo; sequence &lt;= hi; sequence++) {
     *     // Do work.
     * }
     * sequencer.publish(lo, hi);
     * </pre>
     * 申请下N个序列值，用来发布事件。
     * @param n the number of sequences to claim
     * @return the highest claimed sequence value
     */
    long next(int n);

    /**
     * Attempt to claim the next event in sequence for publishing.  Will return the
     * number of the slot if there is at least <code>requiredCapacity</code> slots
     * available.
     * 尝试申请下一个序列值用来发布事件，这个是无阻塞的方法
     * @return the claimed sequence value
     * @throws InsufficientCapacityException thrown if there is no space available in the ring buffer.
     */
    long tryNext() throws InsufficientCapacityException;

    /**
     * Attempt to claim the next n events in sequence for publishing.  Will return the
     * highest numbered slot if there is at least <code>requiredCapacity</code> slots
     * available.  Have a look at {@link Sequencer#next()} for a description on how to
     * use this method.
     * 尝试申请下N个序列值用来发布事件，这个是无阻塞的方法。
     * @param n the number of sequences to claim
     * @return the claimed sequence value
     * @throws InsufficientCapacityException thrown if there is no space available in the ring buffer.
     */
    long tryNext(int n) throws InsufficientCapacityException;

    /**
     * Publishes a sequence. Call when the event has been filled.
     * 在给定的序列值上发布事件，当填充好事件后会调用这个方法
     * @param sequence the sequence to be published.
     */
    void publish(long sequence);

    /**
     * Batch publish sequences.  Called when all of the events have been filled.
     * 在给定的序列返回上发布事件，当填充好事件后会调用这个方法
     * @param lo first sequence number to publish
     * @param hi last sequence number to publish
     */
    void publish(long lo, long hi);
}