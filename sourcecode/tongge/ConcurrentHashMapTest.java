package tongge;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fourous
 * @date: 2020/3/27
 * @description: ConcurrentHashMap测试历程
 */
public class ConcurrentHashMapTest {
    Map<Integer, Integer> map = new ConcurrentHashMap<>();

    /**
     * 这里是不安全的，不要以为concurrentHashMap 都是安全操作
     * 有可能线程在放元素会发生覆盖问题
     *
     * @param key
     * @param value
     */
    public void unSafeUpdate(Integer key, Integer value) {
        Integer oldValue = map.get(key);
        if (oldValue == null) {
            map.put(key, value);
        }
    }

    /**
     * 这里其实取巧了，如果判断Value为特定值而不是null的话，这个就是没法保证了，会回到上一个问题上
     *
     * @param key
     * @param value
     */
    public void safeUpdate(Integer key, Integer value) {
        map.putIfAbsent(key, value);
    }

    /**
     * ConcurrentHashMap还提供了另一个方法叫replace(K key, V oldValue, V newValue)可以解决这个问题。
     * 但是一定要注意，当newValue值不能为null不然会删除这个元素
     *
     * @param key
     * @param value
     */
    public void safeUpdateVice(Integer key, Integer value) {
        map.replace(key, 1, value);
    }
}
