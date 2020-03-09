import java.util.Scanner;

/**
* @author fourous
* @date: 2020/3/9
* @description: 写出一个程序，接受一个十六进制的数，输出该数值的十进制表示
 * 对于进制转换，Java或者Python高级语言，都有天然的Integer可以使用
*/
public class oj_5 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        while (sc.hasNext()){
            String str=sc.next().substring(2);
            System.out.println(Integer.parseInt(str,16));
        }
    }
}
