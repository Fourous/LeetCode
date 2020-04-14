package tongge;

/**
 * Created by ZhiHui Liu on 2020/4/13.
 * 拒绝策略
 */
public interface RejectPolicy {
    /**
     * 拒绝策略接口
     *
     * @param runnable
     * @param myThreadPoolExecutor
     */
    void reject(Runnable runnable, MyThreadPoolExecutor myThreadPoolExecutor);
}
