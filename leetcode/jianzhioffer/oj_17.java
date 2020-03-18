package jianzhioffer;

/**
 * @author fourous
 * @date: 2020/3/18
 * @description: 打印从1到n的N位数
 * 这题其实重点就在求解10的N次方，和16题很像，我们用快速幂很快就能求出来
 * 这题变一下，如果不用Int存的话，这题就很难直接这样解了，只能用大数加法来控制了
 */
public class oj_17 {
    /**
     * 快速幂方法
     * @param n
     * @return
     */
    public int[] printNumbers(int n) {
        //求解10的N次方
        int res = 1;
        int number = 10;
        while (n > 0) {
            if ((n & 1) == 1) {
                res *= number;
            }
            number *= number;
            n >>= 1;
        }
        int[] result = new int[res - 1];
        for (int i = 1; i < res; i++) {
            result[i - 1] = i;
        }
        return result;
    }

}
