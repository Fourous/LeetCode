package tongge;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author fourous
 * @date: 2020/3/24
 * @description: ABA问题测试
 */
public class ABATest {
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(new Stack.Node(3));
        stack.push(new Stack.Node(2));
        stack.push(new Stack.Node(1));
        new Thread(() -> {
            stack.pop();
        }).start();
        // 这里可以看到，后面又把A入栈了，这个是不正常的
        new Thread(() -> {
            Stack.Node A = stack.pop();
            Stack.Node B = stack.pop();
            stack.push(A);
        }).start();

//        testAtomInteger();
        testAtomicStampedReference();
    }

    /**
     * 测试AtomInteger的ABA问题
     */
    public static void testAtomInteger() {
        AtomicInteger atomicInteger = new AtomicInteger(1);

        new Thread(() -> {
            int value = atomicInteger.get();
            System.out.println("Thread1 read value is " + value);
            LockSupport.parkNanos(1000000000L);
            if (atomicInteger.compareAndSet(value, 3)) {
                System.out.println("Thread 1 update " + value + "to 3");
            } else {
                System.out.println("Thread 1 update fail");
            }
        }).start();

        new Thread(() -> {
            int value = atomicInteger.get();
            System.out.println("Thread2 read value is " + value);
            if (atomicInteger.compareAndSet(value, 2)) {
                System.out.println("Thread2 update " + value + "to 2");
                // do something
                value = atomicInteger.get();
                System.out.println("Thread2 read value is " + value);
            } else {
                System.out.println("thread2 update fail");
            }
        }).start();
    }

    public static void testAtomicStampedReference() {
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);
        new Thread(() -> {
            int[] stampHolder = new int[1];
            int value = atomicStampedReference.get(stampHolder);
            int stamp = stampHolder[0];
            System.out.println("Thread1 read value is " + value + "stamp is" + stamp);
            LockSupport.parkNanos(1000000000L);
            if (atomicStampedReference.compareAndSet(value, 3, stamp, stamp + 1)) {
                System.out.println("Thread 1 update " + value + "to 3");
            } else {
                System.out.println("Thread 1 update fail");
            }
        }).start();

        new Thread(() -> {
            int[] stampHolder = new int[1];
            int value = atomicStampedReference.get(stampHolder);
            int stamp = stampHolder[0];
            System.out.println("Thread2 read value is " + value + "stamp is" + stamp);
            if (atomicStampedReference.compareAndSet(value, 2, stamp, stamp + 1)) {
                System.out.println("Thread2 update " + value + "to 2");
                // do something
                value = atomicStampedReference.get(stampHolder);
                stamp = stampHolder[0];
                System.out.println("Thread2 read value is " + value + "stamp is" + stamp);
                if (atomicStampedReference.compareAndSet(value, 1, stamp, stamp + 1)) {
                    System.out.println("Thread2 update " + value + "to 1");
                }
            } else {
                System.out.println("thread2 update fail");
            }
        }).start();
    }

    static class Stack {
        private AtomicReference<Node> top = new AtomicReference<>();

        //栈中节点信息
        static class Node {
            int value;
            Node next;

            public Node(int value) {
                this.value = value;
            }
        }

        // 出栈
        public Node pop() {
            for (; ; ) {
                Node t = top.get();
                if (t == null) {
                    return null;
                }
                // 栈顶下个节点
                Node next = t.next;
                if (top.compareAndSet(t, next)) {
                    t.next = null;
                    return t;
                }
            }
        }

        // 入栈
        public void push(Node node) {
            for (; ; ) {
                Node next = top.get();
                node.next = next;
                if (top.compareAndSet(next, node)) {
                    return;
                }
            }
        }
    }
}
