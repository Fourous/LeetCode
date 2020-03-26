package tongge;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/3/26
 * @description: AQS测试历程
 * 可以通过历程看到，通过改写两个方法就可以实现锁
 */
public class AQSTest {
    private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        public boolean tryAcquire(int acquires) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean tryRelease(int releases) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        AQSTest aqsTest = new AQSTest();
        CountDownLatch countDownLatch = new CountDownLatch(100);
        IntStream.range(0, 100).forEach(i -> new Thread(() -> {
            aqsTest.lock();
            try {
                IntStream.range(0, 1000).forEach(j -> {
                    count++;
                });
            } finally {
                aqsTest.unlock();
            }
            countDownLatch.countDown();
        }, "tt-" + i).start());
        countDownLatch.await();
        // 结果是100000 证明锁成功
        System.out.println(count);
    }
}
