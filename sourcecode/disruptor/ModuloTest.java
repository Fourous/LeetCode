package disruptor;

import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/4/21
 * @description: 用位运算代替取模运算
 */
public class ModuloTest {
    public static void main(String[] args) {
        // 注意，如果ab是任意数，这个方法是不能使用的
        IntStream.range(0, 100).forEach(i -> {
            int a = number();
            int b = number();
            if (a < b) {
                int temp = b;
                b = a;
                a = temp;
            }
            System.out.println("a: " + a);
            System.out.println("b: " + b);
            System.out.println(a % b);
            System.out.println(a & (b - 1));
            System.out.println("*******************");
        });
        /**
         * 使b为2的N次方
         * 可以看到，如果取模数为2的N次方，得到的两个运算结果完全一样
         */
        int c = 16;
        for (int i = 0; i < 100; i++) {
            int d = 0;
            while (d < c) {
                d = number();
            }
            System.out.println("d: " + d);
            System.out.println(d % c);
            System.out.println(d & (c - 1));
        }
    }

    // 取1-1000的数字
    static int number() {
        return 1 + (int) (Math.random() * (1000 - 1 + 1));
    }
}
