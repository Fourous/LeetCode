package tongge;

/**
 * @author fourous
 * @date: 2020/3/25
 * @description: java 伪共享历程
 * 所谓伪共享，是多线程在修改不想干的两个遍历时候，由于两个遍历同时在一个缓存行里面，导致缓存频繁加载遍历的情况
 */
public class FalseSharing {
    public static void main(String[] args) throws InterruptedException {
        testPseudoSharing(new Pointer());
    }

    // 测试伪共享
    public static void testPseudoSharing(Pointer pointer) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000_0000; i++) {
                pointer.x.value++;
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000_0000; i++) {
                pointer.y.value++;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(pointer);
    }

    /**
     * 这里x,y必须是可见的，内存屏障
     * volatile我们知道作用一个是对线程可见，一个是禁止CPU重排序
     */
    public static class Pointer {
        Mylong x = new Mylong();
        Mylong y = new Mylong();
    }

    @sun.misc.Contended
    public static class Mylong {
        volatile long value;
    }
}
