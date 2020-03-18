import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/12
 * @description: 计算一个数字的立方根，不使用库函数
 * 原型：
 * public static double getCubeRoot(double input)
 * 输入:double 待求解参数
 * 返回值:double  输入参数的立方根，保留一位小数
 * 此题是个通用题，重点考察的高次方程的求解，我们知道在5次高阶方程上面群论可以证明无法直接求解，目前最好方式使用牛顿法迭代求解
 * 归纳一下关于牛顿法求解立方根问题：
 * 对于二次方程，泰勒展开置换可得：（x+(num/x)）/2
 * 对于三次方程：(2*x+(num/(x*x)))/3
 * 通用求解就是y=x-(f(x)/f'(x))
 * 这里还需要注意保留小数点输出，最好用String.format，不要直接字符串切割
 */
public class oj_18 {
    public static double getCubicRoot(double num) {
        if (num == 0) {
            return num;
        } else {
            double m = num;
            double n = ((2 * m) + (num / (m * m))) / 3;
            while (m - n > 0.000000001 || m - n < (-0.00000001)) {
                m = n;
                n = ((2 * m) + (num / (m * m))) / 3;
            }
            return n;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            double number = Double.valueOf(scan.nextLine());
            System.out.println(String.format("%.1f", getCubicRoot(number)));
        }
    }
}
