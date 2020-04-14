package tongge;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ZhiHui Liu on 2020/4/13.
 * 手写线程池
 */
public class MyThreadPoolExecutor implements Executor {
    /**
     * 线程池名称
     */
    private String name;
    /**
     * 核心线程个数
     */
    private int coreSize;
    /**
     * 最大线程个数
     */
    private int maxSize;
    /**
     * 线程序列号
     */
    private AtomicInteger sequence = new AtomicInteger(0);
    /**
     * 阻塞队列
     */
    private BlockingQueue<Runnable> taskQueue;
    /**
     * 拒绝策略
     */
    private RejectPolicy rejectPolicy;
    /**
     * 使用线程数
     */
    private AtomicInteger runningThread = new AtomicInteger(0);

    public MyThreadPoolExecutor(String name, int coreSize, int maxSize, BlockingQueue<Runnable> taskQueue, RejectPolicy rejectPolicy) {
        this.name = name;
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.taskQueue = taskQueue;
        this.rejectPolicy = rejectPolicy;
    }

    @Override
    public void execute(Runnable task) {
        int count = runningThread.get();
        if (count < coreSize) {
//            int max =
        }
    }

    private boolean addWorder(Runnable newATask, boolean core) {
        /**
         * CAS + 自旋
         */
        for (; ; ) {
            int count = runningThread.get();

        }
    }

    public static void main(String[] args) {

    }
}
