package sort;

/**
 * @author fourous
 * @date: 2020/3/8
 * @description: 堆排序是对简单选择排序的改进，大顶堆是父节点大于等于其子节点，小顶相反，其复杂度为nlogn
 * 堆排序是将数据看成是完全二叉树、根据完全二叉树的特性来进行排序的一种算法
 */
public class HeapSort {
    /**
     * 排序入口
     *
     * @param intArray
     */
    public static void heapSort(int[] intArray) {
        // 构建大顶堆
        for (int i = intArray.length / 2 - 1; i >= 0; i--) {
            adjustHeap(intArray, i, intArray.length);
        }
        for (int j = intArray.length - 1; j > 0; j--) {
            swap(intArray, 0, j);
            adjustHeap(intArray, 0, j);
        }
        for (int k = 0; k < intArray.length; k++) {
            System.out.println(intArray[k]);
        }
    }

    /**
     * 调整大顶堆，前提是大顶堆已经构建
     *
     * @param intArray
     * @param i
     * @param length
     */
    public static void adjustHeap(int[] intArray, int i, int length) {
        int temp = intArray[i];
        for (int k = 2 * i + 1; k < length; k = 2 * k + 1) {
            // 左节点开始也就是2*i+1处
            if (k + 1 < length && intArray[k] < intArray[k + 1]) {
                k++;
            }
            if (intArray[k] > temp) {
                intArray[i] = intArray[k];
                i = k;
            } else {
                break;
            }
        }
        intArray[i] = temp;
    }

    /**
     * 交换元素
     *
     * @param intArray
     * @param i
     * @param j
     */
    public static void swap(int[] intArray, int i, int j) {
        int temp = intArray[i];
        intArray[i] = intArray[j];
        intArray[j] = temp;
    }

    public static void main(String[] args) {
        int[] ints = {4, 5, 6, 3, 2, 1, 8, 9, 3, 16, 8, 23, 6, 123, 43, 54, 678, 8, 6, 32, 33};
        heapSort(ints);
    }
}
