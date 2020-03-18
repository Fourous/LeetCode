import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/10
 * @description: 考察Java的进制转换，如果做的比较复杂点，先讲十进制转换为二进制，然后数出其个数，
 * 此题目既然是二进制，可以直接使用Java的位运算来解决，因为其数值都是默认存的二进制
 */
public class oj_13 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            int num = scan.nextInt();
            int count = 0;
            while (num > 0) {
                if ((num & 1) > 0) {
                    count++;
                }
                num = num >> 1;
            }
            System.out.println(count);
        }
    }
}
