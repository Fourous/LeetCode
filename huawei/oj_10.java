import java.util.HashSet;
import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/10
 * @description: 编写一个函数，计算字符串中含有的不同字符的个数。字符在ACSII码范围内(0~127)，换行表示结束符，不算在字符里。不在范围内的不作统计。
 * 技巧，像这样统计不重复字眼，用Set会很省事
 */
public class oj_10 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String string = scan.nextLine();
            char[] chars = string.toCharArray();
            HashSet<Character> hashSet = new HashSet<>();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] >= 0 && chars[i] <= 127) {
                    hashSet.add(chars[i]);
                }
            }
            System.out.println(hashSet.size());
        }
    }
}
