package sort;

/**
 * @author fourous
 * @date: 2020/3/8
 * @description: 希尔排序，针对插入排序每次只能移动一位的低效场景,希尔排序的复杂度和希尔增量有关系
 * 例如
 * 当增量序列 2x 时，时间复杂度为 n2
 * 当增量序列 3x+1 时，时间复杂度为 n3/2
 * 这里注意，希尔排序是不稳定的
 */
public class ShellSort {
    public static void shellSort(int[] intArray) {
        int len = intArray.length;
        int h = 1;
        while (h < len / 3) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (int i = h; i < len; i++) {
                for (int j = i; j >= h; j = j - h) {
                    if (intArray[j] > intArray[j - h]) {
                        break;
                    }
                    exchange(intArray, j - h, j);
                }
            }
            h = h / 3;
        }
        for (int k = 0; k < intArray.length; k++) {
            System.out.println(intArray[k]);
        }
    }

    /**
     * 数组交换
     *
     * @param intArray
     * @param i
     * @param j
     */
    public static void exchange(int[] intArray, int i, int j) {
        int temp = intArray[i];
        intArray[i] = intArray[j];
        intArray[j] = temp;
    }

    public static void main(String[] args) {
        int[] ints = {4, 5, 6, 3, 2, 1, 8, 9, 3, 16, 8, 23, 6, 123, 43, 54, 678, 8, 6, 32, 33};
        shellSort(ints);
    }
}
