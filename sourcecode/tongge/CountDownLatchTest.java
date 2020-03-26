package tongge;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/3/26
 * @description: CountDownLatch测试历程
 * 第一段，5个辅助线程等待开始的信号，信号由主线程发出
 * 所以5个辅助线程调用startSignal.await()方法等待开始信号
 * 当主线程的事儿干完了，调用startSignal.countDown()通知辅助线程开始干活。
 * 第二段，主线程等待5个辅助线程完成的信号
 * 信号由5个辅助线程发出，所以主线程调用doneSignal.await()方法等待完成信号
 * 5个辅助线程干完自己的活儿的时候调用doneSignal.countDown()方法发出自己的完成的信号
 * 当完成信号达到5个的时候，唤醒主线程继续执行后续的逻辑
 */
public class CountDownLatchTest {
    /**
     * 在这个过程里面其实可以想一下线程执行顺序
     * 需要线程顺序执行或者按照一定的限制顺序执行，使用CountDownLatch有很好的优势，很灵活
     * 执行结束发送信号，需要发多少个信号就需要多少个Latch
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch endSignal = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println("Aid Thread is waiting for");
                    startSignal.await();
                    // do something
                    System.out.println("Aid Thread is doing something");
                    endSignal.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            },"tt-" + i).start();
        }
        Thread.sleep(2000);
        System.out.println("Main Thread is doing something");
        startSignal.countDown();
        System.out.println("Main Thread is waiting for Aid Thead finished");
        endSignal.await();
        System.out.println("Main Thead is doing something after all Thread finished");
    }
}
