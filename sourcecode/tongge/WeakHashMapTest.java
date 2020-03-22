package tongge;

import java.util.WeakHashMap;

/**
 * @author fourous
 * @date: 2020/3/21
 * @description: WeakHashMap测试历程
 */
public class WeakHashMapTest {
    public static void main(String[] args) {
        WeakHashMap<String, Integer> weakHashMap = new WeakHashMap<>();
        weakHashMap.put(new String("1"), 1);
        weakHashMap.put(new String("2"), 2);
        weakHashMap.put(new String("3"), 3);
        // 以这种做法装入的数据是在常量池中的，不会被GC
        weakHashMap.put("4", 4);

        String key = null;
        for (String s : weakHashMap.keySet()) {
            if ("3".equals(s)) {
                // 这里是用Key强引用了这个3值
                key = s;
            }
        }
        System.gc();
        System.out.println(weakHashMap);
        weakHashMap.put(new String("5"), 5);
        System.out.println(weakHashMap);
        // 断掉强引用
        key = null;
        System.gc();
        System.out.println(weakHashMap);
    }
}
