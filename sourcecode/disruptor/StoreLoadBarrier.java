package disruptor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fourous
 * @date: 2020/4/20
 * @description: 测试SL中的乱序问题
 */
public class StoreLoadBarrier {
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        int A = 0;
        int B = 0;
        int[] arr = {A,B};

        new Thread(() -> {
            arr[0] = 1;
            System.out.println("A" +arr[0]);
            System.out.println("B" + arr[1]);
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            arr[1] = 2;
            System.out.println("A" + arr[0]);
            System.out.println("B" + arr[1]);
            countDownLatch.countDown();
        }).start();

        /**
         * 出现0 1的情况
         * 但是最终结果还是3
         */
        countDownLatch.await();
        System.out.println(A + B);
    }
}
