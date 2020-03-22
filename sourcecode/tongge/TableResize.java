package tongge;

import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/21
 * @description: 这个操作是HashMap的Resize操作，就是扩容的时候，会扩容到当前Capacity最近的2次方值
 * 也就是只要扩容，一定会扩容到原来容量的两倍
 */
public class TableResize {
    public static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            int input = Integer.valueOf(scan.nextLine());
            System.out.println(tableSizeFor(input));
        }
    }
}
