package tongge;

/**
 * @author fourous
 * @date: 2020/3/26
 * @description: synchronized同步历程
 */
public class synchronizedTest {
    /**
     * 在方法上使用synchronized的时候要注意，会隐式传参，分为静态方法和非静态方法
     * 静态方法上的隐式参数为当前类对象，非静态方法上的隐式参数为当前实例this。
     * 另外，多个synchronized只有锁的是同一个对象，它们之间的代码才是同步的，这一点在使用synchronized的时候一定要注意
     */
    public static final Object lock = new Object();

    //锁的是SynchronizedTest.class对象
    public static synchronized void sync1() {

    }

    //锁的是SynchronizedTest.class对象
    public static void sync2() {
        synchronized (synchronizedTest.class) {
        }
    }

    // 锁的是当前实例this
    public synchronized void sync3() {
    }

    public void sync4() {
        //锁的是当前实例this
        synchronized (this) {
        }
    }

    public void sync5() {
        // 锁的是指定对象lock
        synchronized (lock) {
        }
    }
}
