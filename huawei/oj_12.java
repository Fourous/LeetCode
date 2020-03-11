import java.util.Arrays;
import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/10
 * @description: 这题又点类似基数排序, java中如果不规定不使用排序包，直接使用排序包速度较快
 * 再就是像这样的OJ，统一使用nextLine方法读取，不然会导致nextInt后面有空格和Null问题发生
 * 排序如果用数组有Arrays.sort 集合有Collections.sort
 */
public class oj_12 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            int num = Integer.valueOf(scan.nextLine());
            String[] list = new String[num];
            for (int i = 0; i < num; i++) {
                list[i] = scan.nextLine();
            }
            Arrays.sort(list);
            for (int k = 0; k < list.length; k++) {
                System.out.println(list[k]);
            }
        }
    }
}
