package grokkingalgorithms;

/**
 * @author fourous
 * @date: 2020/3/17
 * @description: 欧几里得算法也叫辗转相除法，而我们熟知的RSA加密，其实用到了扩展的欧几里得算法
 */
public class gcd {
    /**
     * 欧几里得算法最容易理解的递归写法
     * 这里其实也不用判断是否ab谁大谁小的问题，网上很多都先做了个交换操作
     *
     * @param a
     * @param b
     * @return
     */
    public static int gcdRecursive(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcdRecursive(b, a % b);
        }
    }

    /**
     * 欧几里得算法的非递归写法
     *
     * @param a
     * @param b
     * @return
     */
    public static int gcd(int a, int b) {
        int temp;
        while (b != 0) {
            temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    /**
     * 在求解最大公约数我们常用的是辗转相除法，但是还有一种很少提到的就是辗转相减法
     * 其实都差不多，只是可能迭代的次数较多，所以很少用，对于大数，就时间会长很多了
     *
     * @param a
     * @param b
     * @return
     */
    public static int tumblingSubtractionRecursive(int a, int b) {
        if (a == b) {
            return a;
        } else if (a > b) {
            return tumblingSubtractionRecursive(b, a - b);
        } else {
            return tumblingSubtractionRecursive(b, b - a);
        }
    }

    /**
     * 辗转相减法非递归写法
     *
     * @param a
     * @param b
     * @return
     */
    public static int tumblingSubtraction(int a, int b) {
        while (a == b) {
            if (a > b) {
                a -= b;
            } else {
                b -= a;
            }
        }
        return a;
    }

    /**
     * 针对多个数的最大公约数
     * 这个也很好理解，选定两个数求GCD，然后用这个GCD和其他数分别求
     *
     * @param num
     * @return
     */
    public static int arrayGcd(int[] num) {
        if (num.length == 1) {
            return num[0];
        } else {
            int ans = num[0];
            for (int i = 0; i < num.length - 1; i++) {
                ans = gcdRecursive(ans, num[i + 1]);
            }
            return ans;
        }
    }

    public static int e_gcd(int a, int b, int x, int y) {
        if (b == 0) {
            x = 1;
            y = 0;
            return a;
        }
        int ans = e_gcd(b, a % b, x, y);
        int temp = x;
        x = y;
        y = temp - a / b * y;
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(tumblingSubtractionRecursive(2, 32));
        int[] nums = {12, 48, 60};
        System.out.println(arrayGcd(nums));
    }
}
