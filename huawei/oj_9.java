import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/10
 * @description:
 */
public class oj_9 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String oriNum = scan.nextLine();
            char[] numChar = oriNum.toCharArray();
            String s = "";
            for (int i = numChar.length - 1; i >= 0; i--) {
                if (s.indexOf(numChar[i]) < 0) {
                    s += numChar[i];
                }
            }
            System.out.println(s);
        }
    }
}
