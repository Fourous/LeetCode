import java.util.Scanner;

/**
 * Q：写出一个程序，接受一个由字母和数字组成的字符串，和一个字符，然后输出输入字符串中含有该字符的个数。不区分大小写。
 *
 * 这题需要注意的是大小写问题，是个陷阱
 * 主要只有String才能转大写，并且读入是String，你需要手动转成Char去比较，或者用String.valueOf比价也行
 */
public class wordNum {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String string = "";
        String one = "";
        char[] ac;
        while(scan.hasNext()){
            int count=0;
            string = scan.nextLine();
            System.out.println(string);
            one = scan.nextLine();
            System.out.println(one);
            ac = string.toCharArray();
            for(int i = 0; i<ac.length;i++){
                if(one.equalsIgnoreCase(String.valueOf(ac[i]))){
                    count++;
                }
            }
            System.out.println(count);
        }
    }

}
