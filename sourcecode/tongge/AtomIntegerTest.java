package tongge;

import java.util.concurrent.atomic.*;

/**
 * @author fourous
 * @date: 2020/3/24
 * @description: 原子类测试历程
 */
public class AtomIntegerTest {
    public static void main(String[] args) {
        /**
         * 测试原子操作基本类型
         */
        // 测试AtomInteger
        System.out.println("测试原子基本类型");
        AtomicInteger atomicInteger = new AtomicInteger(1);
        atomicInteger.incrementAndGet();
        atomicInteger.incrementAndGet();
        atomicInteger.compareAndSet(3, 666);
        System.out.println(atomicInteger.get());

        // 测试AtomStampedReference,这里测试差不多，也可以测试Mark都是一样用法
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);
        // 这里可以实验一下就知道，一旦expected设置不符合，就保持原样不改变值，这两个值是同步的，而且通过Mark或者Stamp，可以有效防止ABA问题
        atomicStampedReference.compareAndSet(1, 2, 1, 3);
        atomicStampedReference.compareAndSet(2, 666, 1, 555);
        System.out.println(atomicStampedReference.getReference());
        System.out.println(atomicStampedReference.getStamp());
        /**
         * 测试数组
         */

        //测试原子数组
        System.out.println("测试原子数组");
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        // getAndAdd(i, 1)
        Integer a = atomicIntegerArray.getAndIncrement(0);
        atomicIntegerArray.getAndAdd(1, 666);
        // getAndAdd(i, 1) + 1
        Integer b = atomicIntegerArray.incrementAndGet(2);
        // getAndAdd(i, delta) + delta
        atomicIntegerArray.addAndGet(3, 666);
        atomicIntegerArray.compareAndSet(4, 0, 666);
        System.out.println(atomicIntegerArray.get(0));
        System.out.println(atomicIntegerArray.get(1));
        System.out.println(atomicIntegerArray.get(2));
        System.out.println(atomicIntegerArray.get(3));
        System.out.println(atomicIntegerArray.get(4));
        System.out.println(atomicIntegerArray.get(5));
        // 这里很容易看花眼，其实是返回值的问题，因为对于数组里面，都会原子加1，只是返回值区别问题罢了
        System.out.println("a=" + a);
        System.out.println("b=" + b);

        /**
         * 测试对象字段
         */
        AtomicReferenceFieldUpdater<User, String> atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "userName");
        AtomicIntegerFieldUpdater<User> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
        User user = new User("fourous", 24);
        atomicReferenceFieldUpdater.compareAndSet(user, "fourous", "this is test");
        atomicIntegerFieldUpdater.compareAndSet(user, 24, 10);
        atomicIntegerFieldUpdater.getAndDecrement(user);
        System.out.println(user);

        /**
         * 高性能原子类
         */
        System.out.println("高性能原子类");
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.add(123);
        System.out.println(longAdder.sum());
        // 这里可以自定义算子，v1初始值为base值
        LongAccumulator longAccumulator = new LongAccumulator((v1, v2) -> v1 * v2 + 2, 2);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(3);
        longAccumulator.accumulate(2);
        System.out.println(longAccumulator.get());
    }

    public static class User {
        /**
         * TODO 需要解释为什么要加volatile关键字
         * 这段为什么需要volatile关键字
         * 这是需要思考的
         */
        public volatile String userName;
        public volatile int age;

        public String getUserName() {
            return userName;
        }

        public User(String userName, int age) {
            this.userName = userName;
            this.age = age;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "name: " + userName + "\n" + "age: " + age;
        }
    }
}
