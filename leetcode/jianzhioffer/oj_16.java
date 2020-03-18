package jianzhioffer;

/**
 * @author fourous
 * @date: 2020/3/18
 * @description: 数值的整次方
 * 文章参考https://leetcode-cn.com/problems/shu-zhi-de-zheng-shu-ci-fang-lcof/solution/mian-shi-ti-16-shu-zhi-de-zheng-shu-ci-fang-kuai-s/
 * 根据题目意思，n取值范围在 [−231, 231 − 1]
 * 那么在用int来存时候，做-n操作会导致溢出所以我们最好是用long存储
 */
public class oj_16 {
    /**
     * 第一反映自然是调库
     * 一般肯定不会考察你这个，当然这也不失为一种方法
     *
     * @param x
     * @param n
     * @return
     */
    public double powFirst(double x, int n) {
        return Math.pow(x, n);
    }

    /**
     * 最捞的做法是直接乘，但是如果N太大，会直接报错时间超了
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        double result = x;
        if (n == 0) {
            return 1.0;
        } else if (n > 0) {
            for (int i = 0; i < n; i++) {
                result *= x;
            }
            return result;
        } else {
            n = 0 - n;
            for (int i = 0; i < n; i++) {
                result *= x;
            }
            return 1 / result;
        }
    }

    /**
     * 上个题解，复杂度达到了O(n)
     * 我们使用快速幂法，能将复杂度降到O(log2n)
     */
    public double pow(double x, int n) {
        if (x == 0) {
            return 0;
        }
        // int32的负数转换会导致溢出
        long b = n;
        double res = 1.0;
        if (b < 0) {
            x = 1 / x;
            b = -b;
        }
        while (b > 0) {
            if ((b & 1) == 1) {
                res *= x;
            }
            x = x * x;
            b >>= 1;
        }
        return res;
    }
}
