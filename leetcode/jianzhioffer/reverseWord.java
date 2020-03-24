package jianzhioffer;

/**
 * @author fourous
 * @date: 2020/3/24
 * @description: 单词反转，并且要去除空格
 * 这里其实考察细节问题，就是去除空格，当空格多了，分割时候会出现空字符串问题，这个时候拼接起来会直接出现空格
 * split里面substring方法要记住两个substring(n)和substring(int begin,int end)
 */
public class reverseWord {
    public static void main(String[] args) {
        String s = "hello i am fourous";
        s.substring(4);
        String[] strings = s.trim().split(" +");
        String result = "";
        for (int i = strings.length - 1; i >= 0; i--) {
            result += strings[i].trim();
            if (i != 0) {
                result += " ";
            }
        }
        System.out.println(result);
    }
}
