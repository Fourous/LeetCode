/**
 * @author fourous
 * @date: 2020/3/8
 * @description: 归并排序是一种典型的分而治之的思想，其表现也很好，复杂度为nlogn，但是需要使用额外内存空间
 */
public class MergeSort {
    /**
     * 排序入口，给定参数
     */
    public static void mergeSort(int[] intArray) {
        sort(intArray, 0, intArray.length - 1);
        for (int k = 0; k < intArray.length; k++) {
            System.out.println(intArray[k]);
        }
    }

    /**
     * 递归调用排序并归并
     *
     * @param intArray
     * @param start
     * @param end
     */
    public static void sort(int[] intArray, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        sort(intArray, start, mid);
        sort(intArray, mid + 1, end);
        merge(intArray, start, mid, end);
    }

    /**
     * 数组归并
     *
     * @param intArray
     * @param start
     * @param mid
     * @param end
     */
    public static void merge(int[] intArray, int start, int mid, int end) {
        int[] temp = new int[intArray.length];
        int k = 0;
        int i = start;
        int j = mid + 1;
        while (i <= mid && j <= end) {
            if (intArray[i] < intArray[j]) {
                temp[k++] = intArray[i++];
            } else {
                temp[k++] = intArray[j++];
            }
        }
        /**
         * 将多余还未复制的值再复制到数组
         * 实际上一下只会执行一个
         */
        while (i <= mid) {
            temp[k++] = intArray[i++];
        }
        while (j <= end) {
            temp[k++] = intArray[j++];
        }
        for (int m = 0; m < k; m++) {
            intArray[start + m] = temp[m];
        }
    }

    public static void main(String[] args) {
        int[] ints = {4, 5, 6, 3, 2, 1, 8, 9, 3, 16, 8, 23, 6, 123, 43, 54, 678, 8, 6, 32, 33};
        mergeSort(ints);
    }
}
