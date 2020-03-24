package tongge;

import sun.misc.Unsafe;

import java.io.*;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/3/24
 * @description: java unsafe测试历程，它提供了一个让我们接近底层的机会
 */
public class UnsafeTest {
    private static Unsafe unsafe;
    private static long offset;

    // 反射拿到Unsafe
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            offset = unsafe.objectFieldOffset(UnsafeTest.Counter.class.getDeclaredField("count"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        User user = new User();
        /**
         * 通过unsafe修改实例中的值
         */
        Field age = user.getClass().getDeclaredField("age");
        unsafe.putInt(user, unsafe.objectFieldOffset(age), 20);
        System.out.println(user.age);

        /**
         * 使用堆外内存
         */
        OffHeapArray offHeapArray = new OffHeapArray(4);
        offHeapArray.set(0, 1);
        offHeapArray.set(1, 2);
        offHeapArray.set(2, 3);
        offHeapArray.set(3, 4);
        offHeapArray.set(2, 5);
        for (int i = 0; i < offHeapArray.size(); i++) {
            System.out.print("this is offHeapArray: " + offHeapArray.get(i) + "\n");
        }
        // 这里注意，一定要释放内存
        offHeapArray.free();
        /**
         * CAS计数器
         */
        Counter counter = new Counter();
        // 这里会提示手动创建线程，暂时只用作测试用
        // 起100个线程，每个线程自增1000次
        /**
         * FixMe 使用CAS+自旋但是得到的值不稳定出现了不一致情况
         */
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        IntStream.range(0, 100)
                .forEach(i -> threadPool.submit(() -> IntStream.range(0, 100)
                        .forEach(j -> counter.increase())));
        threadPool.shutdown();
        Thread.sleep(2000);
        System.out.println("count is:  " + counter.getCount());
        /**
         * 多种实例化方式
         */
        // 构造方法
        UserTestForUnsafe user1 = new UserTestForUnsafe();
        // Class 实际也是反射方式
        UserTestForUnsafe user2 = UserTestForUnsafe.class.newInstance();
        // 反射
        UserTestForUnsafe user3 = UserTestForUnsafe.class.getConstructor().newInstance();
        // 克隆
        UserTestForUnsafe user4 = (UserTestForUnsafe) user1.clone();
        // 反序列化
        UserTestForUnsafe user5 = unserialize(user1);
        // unsafe
        UserTestForUnsafe user6 = (UserTestForUnsafe) unsafe.allocateInstance(UserTestForUnsafe.class);

        System.out.println(user1.age);
        System.out.println(user2.age);
        System.out.println(user3.age);
        System.out.println(user4.age);
        System.out.println(user5.age);
        // 这里可以看到，输出不是10就是0，只是开辟了空间，并没有执行构造函数
        System.out.println(user6.age);
    }

    /**
     * 通过unsafe抛异常
     */
    public static void readFileException() {
        unsafe.throwException(new IOException());
    }

    public static class User {
        private int age;
        private String name;

        public User() {
            this.age = 10;
        }
    }

    /**
     * 使用堆外内存来构建数组
     */
    public static class OffHeapArray {
        private static final int INT = 4;
        private long size;
        private long address;

        public OffHeapArray(int size) {
            this.size = size;
            address = unsafe.allocateMemory(size * INT);
        }

        public int get(long i) {
            return unsafe.getInt(address + i * INT);
        }

        public void set(long i, int value) {
            unsafe.putInt(address + i * INT, value);
        }

        public long size() {
            return size;
        }

        public void free() {
            unsafe.freeMemory(address);
        }
    }

    /**
     * 编写CAS计数器
     */
    public static class Counter {
        private volatile int count = 0;

        public void increase() {
            int before = count;
            while (!unsafe.compareAndSwapInt(this, offset, before, before + 1)) {
                count = before;
            }
        }

        public int getCount() {
            return count;
        }
    }

    /**
     * 多种实例化方式
     */
    private static UserTestForUnsafe unserialize(UserTestForUnsafe user) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("user.txt"));
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("user.txt"));
        UserTestForUnsafe user5 = (UserTestForUnsafe) objectInputStream.readObject();
        objectInputStream.close();
        return user5;
    }
}

