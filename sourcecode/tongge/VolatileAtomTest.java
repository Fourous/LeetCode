package tongge;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/3/26
 * @description: Volatile原子性历程
 */
public class VolatileAtomTest {
    public static volatile int count = 0;
    public static AtomicInteger counter = new AtomicInteger(0);

    /**
     * 这里使用synchronized 就可以得到100000是正确的，所以volatile虽然保证可见，但是并不保证其原子操作
     */
    public static void increment() {
        count++;
        counter.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        IntStream.range(0, 100).forEach(i -> new Thread(() -> {
            IntStream.range(0, 1000).forEach(j -> increment());
            countDownLatch.countDown();
        }).start());
        countDownLatch.await();
        // 肯定不足100000
        System.out.println(count);
        // 100000
        System.out.println(counter.get());
    }
}
