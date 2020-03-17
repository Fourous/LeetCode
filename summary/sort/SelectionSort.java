package sort;

/**
 * @author fourous
 * @date: 2020/3/8
 * @description: 选择排序是最容易理解的一种排序算法，但是其复杂度也是最高的，无论什么情况都是n2，比较适用于数据规模较少的情况
 * 唯一的好处就是容易理解，不耗费额外内存
 */
public class SelectionSort {
    public static void selectionSort(int[] intArray) {
        for (int i = 0; i < intArray.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < intArray.length; j++) {
                if (intArray[j] < intArray[min]) {
                    min = j;
                }
            }
            if (i != min) {
                int temp = intArray[i];
                intArray[i] = intArray[min];
                intArray[min] = temp;
            }
        }
        for (int k = 0; k < intArray.length; k++) {
            System.out.println(intArray[k]);
        }
    }

    public static void main(String[] args) {
        int[] ints = {4, 5, 6, 3, 2, 1, 8, 9, 3, 16, 8, 23, 6, 123, 43, 54, 678, 8, 6, 32, 33};
        selectionSort(ints);
    }
}
