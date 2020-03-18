package grokkingalgorithms;

/**
 * @author fourous
 * @date: 2020/3/17
 * @description: 快速排序，基本被认为是现在最快的排序算法，复杂度达到O(nlogn)
 * 一般来说快排会牵扯到选择基准问题，因为如果元素是排序好了的，恰好选到了左边或者最右边元素，这就导致出现复杂度较高情况发生
 * 算法导论有较详细的说明，例如三数中值等等，这里我们假设数组是随机的，选择最右边元素作为基准
 */
public class QuickSort {
    public static void quickSort(int[] num, int start, int end) {
        if (start < end) {
            int index = partition(num, start, end);
            quickSort(num, start, index - 1);
            quickSort(num, index + 1, end);
        }
    }

    public static int partition(int[] num, int start, int end) {
        int base = num[start];
        int i = start;
        int j = end;
        while (i < j) {
            while (i < j && num[j] >= base) {
                j--;
            }
            while (i < j && num[i] <= base) {
                i++;
            }
            swap(num, i, j);
        }
        swap(num, start, i);
        return i;
    }

    public static void swap(int[] num, int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 7, 4, 5, 0, 2, 4, 6, 1, 4, 7, 8, 3, 1, 5, 9, 3};
        quickSort(nums, 0, nums.length - 1);
        for (int m : nums) {
            System.out.print(m + ",");
        }
    }
}
