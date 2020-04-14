package disruptor;

import sun.misc.Contended;

/**
 * @author fourous
 * @date: 2020/4/14
 * @description: 消除伪共享问题
 * 增大数组元素的间隔使得由不同线程存取的元素位于不同的缓存行上，以空间换时间
 * 了解classmexer.jar的使用
 */
public class FalseSharing implements Runnable {
    public final static long ITERATIONS = 500L * 1000L * 100L;
    private int arrayIndex = 0;

    private static ValuePadding[] longs;

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        for (int i = 1; i < 10; i++) {
            System.gc();
            final long start = System.currentTimeMillis();
            runTest(i);
            System.out.println("Thread num " + i + " duration = " + (System.currentTimeMillis() - start));
        }

    }

    private static void runTest(int NUM_THREADS) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        longs = new ValuePadding[NUM_THREADS];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new ValuePadding();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = 0L;
        }
    }

    /**
     * 组成8个long型
     * 消除伪共享因素
     * <p>
     * Thread num 1 duration = 465
     * Thread num 2 duration = 445
     * Thread num 3 duration = 455
     * Thread num 4 duration = 447
     * Thread num 5 duration = 603
     * Thread num 6 duration = 678
     * Thread num 7 duration = 688
     * Thread num 8 duration = 735
     * Thread num 9 duration = 817
     * 实验证明，加入Contended确实要好很多，并且在刚开始的表现甚至比填充效果要好
     * 最好使用Contended因为计算机的组成不一样
     */
    public final static class ValuePadding {
        protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
        protected long p9, p10, p11, p12, p13, p14;
        protected long p15;
    }

    /**
     * Thread num 1 duration = 448
     * Thread num 2 duration = 2463
     * Thread num 3 duration = 2879
     * Thread num 4 duration = 3766
     * Thread num 5 duration = 4576
     * Thread num 6 duration = 5065
     * Thread num 7 duration = 4679
     * Thread num 8 duration = 4718
     * Thread num 9 duration = 4240
     */
    @Contended
    public final static class ValueNoPadding {
        // protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
        // protected long p9, p10, p11, p12, p13, p14, p15;
    }
}
