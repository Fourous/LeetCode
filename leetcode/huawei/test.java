import java.util.*;
/**
* @author fourous
* @date: 2020/3/15
* @description: 此题当时没做出来
 * 给定一个数组[1,2,3,4,5,6]，求出此数组组成的24小时制的最大时间，例如此数组最大为24:56:31
*/
public class test {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String str = scan.nextLine();
            str.substring(1, str.length() - 1);
            String[] strings = str.split(",");
            int[] number = new int[strings.length];
            for (int i = 0; i < strings.length; i++) {
                number[i] = Integer.valueOf(strings[i]);
            }
            Arrays.sort(number);
            String string = "";
            for (int j = number.length - 1; j >= 0; j--) {
                if ((j + 1) % 2 == 0) {
                    string += ":";
                    string += number[j];
                } else {
                    string += number[j];
                }

            }
            System.out.println(string);
        }
    }
}
