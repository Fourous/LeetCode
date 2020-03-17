/**
 * @author fourous
 * @date: 2020/3/8
 * @description: 冒泡排序是最简单一种排序，但是其复杂度也是最高的，为n2
 */
public class BubbleSort {
    public static void bubbleSort(int[] intArray) {
        for (int i = 0; i < intArray.length - 1; i++) {
            for (int j = 0; j < intArray.length - 1 - i; j++) {
                if (intArray[j] > intArray[j + 1]) {
                    int temp = intArray[j];
                    intArray[j] = intArray[j + 1];
                    intArray[j + 1] = temp;
                }
            }
        }
        // 打印数组
        for (int k = 0; k < intArray.length; k++) {
            System.out.println(intArray[k]);
        }
    }

    public static void main(String[] args) {
        int[] ints = {4, 5, 6, 3, 2, 1, 8, 9, 3, 16, 8, 23, 6, 123, 43, 54, 678, 8, 6, 32, 33};
        bubbleSort(ints);
    }
}
