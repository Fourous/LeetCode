package tongge;

import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author fourous
 * @date: 2020/3/26
 * @description: 通过读写锁，来实现高效安全的TreeMap
 */
public class TreeMapSafe {
    private final TreeMap<String, Object> treeMap = new TreeMap<>();
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = reentrantReadWriteLock.writeLock();
    private final Lock readLock = reentrantReadWriteLock.readLock();

    public void put(String key, Object object) {
        writeLock.lock();
        try {
            treeMap.put(key, object);
        } finally {
            writeLock.unlock();
        }
    }

    public Object get(String key) {
        readLock.lock();
        try {
            return treeMap.get(key);
        } finally {
            readLock.unlock();
        }
    }
}
