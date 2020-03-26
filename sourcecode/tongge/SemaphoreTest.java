package tongge;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/3/26
 * @description: 采用信号量来控制许可请求来限流
 */
public class SemaphoreTest {
    public static final Semaphore SEMAPHORE = new Semaphore(100);
    public static final AtomicInteger failCount = new AtomicInteger(0);
    public static final AtomicInteger successCount = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        IntStream.range(0, 1000).forEach(i -> new Thread(() -> seckill()).start());
    }

    public static boolean seckill() {
        if (!SEMAPHORE.tryAcquire()) {
            failCount.incrementAndGet();
            return false;
        }
        try {
            Thread.sleep(2000);
            System.out.println("seckill success and count is " + successCount.incrementAndGet());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            SEMAPHORE.release();
        }
        return true;
    }
}
