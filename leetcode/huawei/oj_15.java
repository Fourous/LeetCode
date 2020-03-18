import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/11
 * @description: 密码要求:
 * 1.长度超过8位
 * 2.包括大小写字母.数字.其它符号,以上四种至少三种
 * 3.不能有相同长度超2的子串重复
 *
 * 技巧，其实这题难点在字符串是否重复这里，一般我们只能通过两个for循环能做
 * 如果编译不通过，一定要看看历程对比，来检查代码错误
 */
public class oj_15 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String string = scan.nextLine();
            if (isLength(string)) {
                if (isObtain(string) && isSubtain(string)) {
                    System.out.println("OK");
                } else {
                    System.out.println("NG");
                }
            } else {
                System.out.println("NG");
            }
        }
    }

    public static boolean isLength(String string) {
        if (string.length() > 8 && string != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isObtain(String string) {
        char[] chars = string.toCharArray();
        int num = 0;
        int upperCase = 0;
        int lowerCase = 0;
        int otherCase = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] <= 'z' && chars[i] >= 'a') {
                lowerCase = 1;
            } else if (chars[i] <= 'Z' && chars[i] >= 'A') {
                upperCase = 1;
            } else if (chars[i] <= '9' && chars[i] >= '0') {
                num = 1;
            } else {
                otherCase = 1;
            }
        }
        if (lowerCase + upperCase + num + otherCase >= 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否有重复的子串
     *
     * @param string
     * @return
     */
    public static boolean isSubtain(String string) {
        for (int i = 0; i < string.length() - 2; i++) {
            String subStr = string.substring(i, i + 3);
            if (string.substring(i + 1).contains(subStr)) {
                return false;
            }
        }
        return true;
    }
}
