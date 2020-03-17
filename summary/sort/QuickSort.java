/**
 * @author fourous
 * @date: 2020/3/8
 * @description: 快排顾名思义，排序速度很快，如果简单从复杂度角度去分析的话，最好情况是nlogn最坏情况是n2
 * 但是一般在大数据项目里面，快排的速度相对于其他排序都要快得多，快速排序又是一种分而治之思想在排序算法上的典型应用。本质上来看，快速排序应该算是在冒泡排序基础上的递归分治法
 * 对绝大多数顺序性较弱的随机数列而言，快速排序总是优于归并排序
 * 很多面试都会考察这个快排，当然不是直接考察，而是考察其思想以及优化
 * 最典型的就是Java的Array中使用的快排，并且对其进行了优化处理
 */
public class QuickSort {
    /**
     * 快排入口函数
     *
     * @param intArray
     */
    public static void quickSort(int[] intArray) {
        sort(intArray, 0, intArray.length - 1);
        for (int k = 0; k < intArray.length; k++) {
            System.out.println(intArray[k]);
        }
    }

    /**
     * 针对基准排序
     *
     * @param intArray
     * @param start
     * @param end
     */
    public static void sort(int[] intArray, int start, int end) {
        if (start < end) {
            int index = partition(intArray, start, end);
            sort(intArray, start, index - 1);
            sort(intArray, index + 1, end);
        }
    }

    /**
     * 划分基准
     *
     * @param intArray
     * @param start
     * @param end
     * @return
     */
    public static int partition(int[] intArray, int start, int end) {
        // 选取基数，这里基数选取默认为第一个，实际上算法导论有更多的选取方式，并且也有其证明
        int privot = intArray[start];
        int i = start;
        int j = end;
        while (i < j) {
            while (intArray[j] >= privot && i < j) {
                j--;
            }
            while (intArray[i] <= privot && i < j) {
                i++;
            }
            swap(intArray, i, j);
        }
        swap(intArray, start, i);
        return i;
    }

    /**
     * 交换
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
        quickSort(ints);
    }
}
