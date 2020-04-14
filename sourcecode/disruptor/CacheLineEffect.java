package disruptor;

import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/4/14
 * @description: 测试利用cache line的特性和不利用cache line的特性
 * CPU每次从主存中拉入数据同时，会同时拉入连续的数据，如果需要的话
 * 这样会加快数据的访问速度
 */
public class CacheLineEffect {
    static long[][] arr;

    public static void main(String[] args) {
        arr = new long[1024 * 1024][];
        IntStream.range(0, 1024 * 1024).forEach(i -> {
            arr[i] = new long[8];
            IntStream.range(0, 8).forEach(j -> {
                arr[i][j] = 0L;
            });
        });
        long sum = 0L;
        long marked = System.currentTimeMillis();
        /**
         * 这里使用了CacheLine，每次取的时候，会直接将连续地址的8个一起取
         * 28 ms
         */
        for (int i = 0; i < 1024 * 1024; i++) {
            for (int j = 0; j < 8; j++) {
                sum = arr[i][j];
            }
        }
        System.out.println("loop times: " + (System.currentTimeMillis() - marked) + "ms");
        marked = System.currentTimeMillis();
        /**
         * 48 ms
         */
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 1024 * 1024; j++) {
                sum = arr[j][i];
            }
        }
        System.out.println("loop times: " + (System.currentTimeMillis() - marked) + "ms");
    }
}
