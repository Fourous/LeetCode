import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/12
 * @description: 正整数A和正整数B 的最小公倍数是指 能被A和B整除的最小的正整数值，设计一个算法，求输入A和B的最小公倍数。
 * <p>
 * 这题比较简单，辗转相除法得到最大公约数，用两个数乘积除以最大公约数就行，这个算法也叫欧几里得算法，RSA就是在其基础改的
 */
public class oj_17 {
    /**
     * 获取最大公约数
     *
     * @param m
     * @param n
     * @return
     */
    public static int gcd(int m, int n) {
        if (m < n) {
            int temp = m;
            m = n;
            n = temp;
        }
        int k;
        while (n != 0) {
            k = m % n;
            m = n;
            n = k;
        }
        return m;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            int m = scan.nextInt();
            int n = scan.nextInt();
            System.out.println((m * n) / gcd(m, n));
        }
    }
}
