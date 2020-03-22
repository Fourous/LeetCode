package tongge;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author fourous
 * @date: 2020/3/21
 * @description: 用LinkedHashMap自身属性用作LRU淘汰策略
 */
public class LRUByLinkedHashMap {
    public static void main(String[] args) {
        LRU<Integer, Integer> lru = new LRU(5, 0.75f);
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        lru.put(4, 4);
        lru.put(5, 5);
        lru.put(6, 6);
        System.out.println(lru.get(4));
        lru.put(6, 666);
        System.out.println(lru);
    }
}

class LRU<K, V> extends LinkedHashMap<K, V> {
    private int capacity;

    public LRU(int capacity, float loadFactor) {
        super(capacity, loadFactor, true);
        this.capacity = capacity;
    }

    /**
     * 这个函数默认在LinkedHashMap中是返回False的，也就是不删除元素
     * 现在我们改写的话，当大于我们设定的Capacity的值会返回true
     *
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
