import java.util.*;
/**
* @author fourous
* @date: 2020/3/9
* @description:
 * 数据表记录包含表索引和数值（int范围的整数），请对表索引相同的记录进行合并，即将相同索引的数值进行求和运算，输出按照key值升序进行输出。
 *
 * 这题有个陷阱，就是输出要升序排列，很小的一个细节问题，如果这里是HashMap要排序才能输出，直接TreeMap会省事
 * 编译器只要是JDK-8以上，用Lambda
*/
public class oj_8 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()){
            int number = scan.nextInt();
            Map<Integer,Integer> map = new TreeMap<>();
            for(int i=0;i<number;i++){
                int key = scan.nextInt();
                int value = scan.nextInt();
                map.merge(key,value,(oldVal,newVal)->oldVal+newVal);
            }
            for(Integer key:map.keySet()){
                System.out.println(key+" "+map.get(key));
            }
        }
    }
}
