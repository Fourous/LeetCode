import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/11
 * @description: 他是这么变换的，大家都知道手机上的字母： 1--1， abc--2, def--3, ghi--4, jkl--5, mno--6, pqrs--7, tuv--8 wxyz--9, 0--0,就这么简单，渊子把密码中出现的小写字母都变成对应的数字，数字和其他的符号都不做变换，
 * 声明：密码中没有空格，而密码中出现的大写字母则变成小写之后往后移一位，如：X，先变成小写，再往后移一位，不就是y了嘛，简单吧。记住，z往后移是a哦。
 * <p>
 * 这题技巧是字母的移动，先读取出字母或者数字的ASCII值，用Integer.valueof
 * 然后通过加减来移位，强转位char值
 */
public class oj_16 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String string = scan.nextLine();
            char[] chars = string.toCharArray();
            String result = "";
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] >= '0' && chars[i] <= '9') {
                    result += chars[i];
                } else if (chars[i] >= 'a' && chars[i] <= 'z') {
                    result += ifLowerCase(chars[i]);
                } else if (chars[i] >= 'A' && chars[i] <= 'Z') {
                    result += ifUpperCase(chars[i]);
                } else {
                    result += chars[i];
                }
            }
            System.out.println(result);
        }
    }

    public static char ifUpperCase(char s) {
        s = (char) (Integer.valueOf(s) + 32);
        if (s == 'z') {
            s = 'a';
        } else {
            s = (char) (Integer.valueOf(s) + 1);
        }
        return s;
    }

    public static char ifLowerCase(char s) {
        if (s == 'a' || s == 'b' || s == 'c') {
            s = '2';
        } else if (s == 'd' || s == 'e' || s == 'f') {
            s = '3';
        } else if (s == 'g' || s == 'h' || s == 'i') {
            s = '4';
        } else if (s == 'j' || s == 'k' || s == 'l') {
            s = '5';
        } else if (s == 'm' || s == 'n' || s == 'o') {
            s = '6';
        } else if (s == 'p' || s == 'q' || s == 'r' || s == 's') {
            s = '7';
        } else if (s == 't' || s == 'u' || s == 'v') {
            s = '8';
        } else if (s == 'w' || s == 'x' || s == 'y' || s == 'z') {
            s = '9';
        }
        return s;
    }
}
