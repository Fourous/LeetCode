package jianzhioffer;

import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/20
 * @description: 正则表达式匹配
 * 实现一个函数用来匹配包含'. '和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（含0次）
 * 也就是对于*前面的一位字符，可以出现0到很多次，.能出现任何字符
 * 刚开始题目都没读懂。。。。
 * 这题算是经典的DP问题
 */
public class oj_19 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String s = scan.nextLine();
            String p = scan.nextLine();
            System.out.println(isMatch(s, p));
        }
    }

    public static boolean isMatch(String s, String p) {
       /*
       s和p可能为空。空的长度就是0，但是这些情况都已经判断过了，只需要判断是否为null即可
       if(p.length()==0&&s.length()==0)
            return true;
            */
        if (s == null || p == null) {
            return false;
        }
        int rows = s.length();
        int columns = p.length();
        boolean[][] dp = new boolean[rows + 1][columns + 1];
        //s和p两个都为空，肯定是可以匹配的，同时这里取true的原因是
        //当s=a，p=a，那么dp[1][1] = dp[0][0]。因此dp[0][0]必须为true。
        dp[0][0] = true;
        for (int j = 1; j <= columns; j++) {
            //p[j-1]为*可以把j-2和j-1处的字符删去，只有[0,j-3]都为true才可以
            //因此dp[j-2]也要为true，才可以说明前j个为true
            if (p.charAt(j - 1) == '*' && dp[0][j - 2]) {
                dp[0][j] = true;
            }
        }

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                char nows = s.charAt(i - 1);
                char nowp = p.charAt(j - 1);
                if (nows == nowp) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    if (nowp == '.') {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else if (nowp == '*') {
                        //p需要能前移1个。（当前p指向的是j-1，前移1位就是j-2，因此为j>=2）
                        if (j >= 2) {
                            char nowpLast = p.charAt(j - 2);
                            //只有p[j-2]==s[i-1]或p[j-2]==‘.’才可以让*取1个或者多个字符：
                            if (nowpLast == nows || nowpLast == '.') {
                                dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                            }
                            //不论p[j-2]是否等于s[i-1]都可以删除掉j-1和j-2处字符：
                            dp[i][j] = dp[i][j] || dp[i][j - 2];
                        }
                    } else {
                        dp[i][j] = false;
                    }
                }
            }
        }
        return dp[rows][columns];
    }
}
