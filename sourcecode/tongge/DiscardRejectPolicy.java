package tongge;

/**
 * Created by ZhiHui Liu on 2020/4/13.
 */
public class DiscardRejectPolicy implements RejectPolicy {
    @Override
    public void reject(Runnable runnable, MyThreadPoolExecutor myThreadPoolExecutor) {
        System.out.println("discard one Thread");
    }
}
