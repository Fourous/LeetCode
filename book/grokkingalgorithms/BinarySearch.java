import java.util.*;

/**
 * @author fourous
 * @date: 2020/3/15
 * @description: 二分查找算法, 注意给定的数组必须是有序的
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] num = {1, 2, 3, 4, 5, 6};
        System.out.println("index is " + binarySearch(num, 5));
        System.out.println("index is " + binarySearch(num, 5, 0, num.length - 1));
    }

    /**
     * 非递归写法
     *
     * @param num
     * @param k
     * @return
     */
    public static int binarySearch(int[] num, int k) {
        int low = 0;
        int high = num.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (num[mid] == k) {
                return mid;
            } else if (num[mid] < k) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 递归写法
     *
     * @param num
     * @param k
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static int binarySearch(int[] num, int k, int beginIndex, int endIndex) {
        int midIndex = (beginIndex + endIndex) / 2;
        if (k < num[beginIndex] || k > num[endIndex] || beginIndex > endIndex) {
            return -1;
        }
        if (k < num[midIndex]) {
            return binarySearch(num, k, beginIndex, midIndex - 1);
        } else if (k > num[midIndex]) {
            return binarySearch(num, k, midIndex + 1, endIndex);
        } else {
            return midIndex;
        }
    }
}
