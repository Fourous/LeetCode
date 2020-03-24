package tongge;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author fourous
 * @date: 2020/3/24
 * @description: ABA问题测试
 */
public class ABATest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        new Thread(()->{
            int value = atomicInteger.get();
            System.out.println("Thread1 read value is "+value);
            LockSupport.parkNanos(1000000000L);

        }).start();
    }
}
