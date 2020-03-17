/**
 * @author fourous
 * @date: 2020/3/8
 * @description: 插入排序，通过找到插入点，从数组开始比较，假定第一个元素有序，通过找到插入点，让后续元素相继后移完成排序
 */
public class InsertSort {
    public static void insertSort(int[] intArray) {
        for (int i = 1; i < intArray.length; i++) {
            int temp = intArray[i];
            int j = i;
            while (j > 0 && temp < intArray[j - 1]) {
                intArray[j] = intArray[j - 1];
                j--;
            }
            if (i != j) {
                intArray[j] = temp;
            }
        }
        for (int k = 0; k < intArray.length; k++) {
            System.out.println(intArray[k]);
        }
    }

    public static void main(String[] args) {
        int[] ints = {4, 5, 6, 3, 2, 1, 8, 9, 3, 16, 8, 23, 6, 123, 43, 54, 678, 8, 6, 32, 33};
        insertSort(ints);
    }
}
