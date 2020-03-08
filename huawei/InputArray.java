import java.util.Scanner;
import java.util.TreeSet;

/**
 * 样例有两组测试
 * 第一组是3个数字，分别是：2，2，1。
 * 第二组是11个数字，分别是：10，20，40，32，67，40，20，89，300，400，15
 */
public class InputArray {
    // 取巧解法，直接使用TreeSet解决
//    public static void main(String[] args){
//        Scanner scanner = new Scanner(System.in);
//        while(scanner.hasNext()){
//            TreeSet<Integer> treeSet = new TreeSet<>();
//            int num = scanner.nextInt();
//            for(int i=0;i<num;i++){
//                treeSet.add(scanner.nextInt());
//            }
//           for(Integer i:treeSet){
//               System.out.println(i);
//           }
//        }
//    }
    public static void sort(int[] sequenceForArray){
        int maxValue = sequenceForArray[0], minValue = sequenceForArray[0];
        // 获取最大和最小值
        for (int i = 1; i < sequenceForArray.length; i++) {
            if (sequenceForArray[i] < minValue) {
                minValue = sequenceForArray[i];
            }
            if (sequenceForArray[i] > maxValue) {
                maxValue = sequenceForArray[i];
            }
        }
        int[] countArray = new int[maxValue-minValue+1];
        // 计数
        for (int i = 0; i < sequenceForArray.length; i++) {
            int value = sequenceForArray[i];
            countArray[value- minValue] += 1;
        }
        // 计算每个值所在位置
        Integer[] tempArray = new Integer[sequenceForArray.length];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = sequenceForArray[i];
        }
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i-1];
        }
        // 整理设置
        for (int i = tempArray.length-1; i >=0 ; i--) {
            int value = tempArray[i];
            sequenceForArray[countArray[value-minValue]-1] = value;
            countArray[value-minValue] -= 1;
        }
        for (int i=0;i<sequenceForArray.length;i++){
            System.out.println(sequenceForArray[i]);
        }
    }
    //使用计数排序是比较优的
    public static void main(String[] args) {

    }
}
