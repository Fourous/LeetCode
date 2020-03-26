package tongge;

import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/3/27
 * @description: CyclicBarrier测试历程
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        IntStream.range(0, 3).forEach(i -> new Thread(() -> {
            System.out.println("Thread waiting");
            System.out.println(Thread.currentThread().getName());
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            System.out.println("Thread finish come");
        }, "tt-" + i).start());
    }
}
