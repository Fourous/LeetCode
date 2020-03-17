package grokkingalgorithms;

/**
 * @author fourous
 * @date: 2020/3/16
 * @description: 选择排序算法
 * 这种排序算法的复杂度为O(n2)
 */
public class SelectionSort {
    public static int[] selectionSort(int[] num) {
        if (num == null) {
            return null;
        }
        for (int i = 0; i < num.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < num.length; j++) {
                if (num[j] < num[min]) {
                    min = j;
                }
            }
            if (i != min) {
                int temp = num[i];
                num[i] = num[min];
                num[min] = temp;
            }
        }
        return num;
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 9, 4, 6, 2, 8, 4, 6, 8, 1};
        selectionSort(nums);
        for (int k = 0; k < nums.length; k++) {
            System.out.println(nums[k]);
        }
    }
}
