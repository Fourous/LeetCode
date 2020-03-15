import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/15
 * @description: 考试题，定义a2+b2=c2,并且 abc互质，给定区间，求出此区间内所有的abc数
 */
public class test2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            int before = Integer.valueOf(scan.nextLine());
            int last = Integer.valueOf(scan.nextLine());
            if (before == 1) {
                before++;
            }
            int result = 0;
            for (int i = before; i < last; i++) {
                for (int j = i + 1; j < last; j++) {
                    int temp = i * i + j * j;
                    if (isD(temp) != 0) {
                        if (isD(temp) < last) {
                            if (isN(i, j) && isN(i, temp) && isN(j, temp)) {
                                result++;
                                System.out.println(i + " " + j + " " + isD(temp));
                            }
                        }
                    }
                }
            }
            if (result == 0) {
                System.out.println("NA");
            }
        }
    }

    public static boolean isN(int a, int b) {
        // 辗转相除
        int m;
        if (a < b) {
            int temp = a;
            a = b;
            b = temp;
        }
        while (b != 0) {
            m = a % b;
            a = b;
            b = m;
        }
        if (a == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static int isD(int num) {
        int def = 0;
        for (int i = 0; i < num; i++) {
            if (i * i == num) {
                return i;
            }
        }
        return def;
    }
}
