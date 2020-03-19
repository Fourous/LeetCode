package jianzhioffer;

/**
 * @author fourous
 * @date: 2020/3/19
 * @description: 统计一个数字在数组中出现的次数
 * 这题看起来比较简单，但是一般面试会直接让你用二分法解决，其实也就是转换成二分法的边界问题
 */
public class oj_53 {
    public int search(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        int leftBound = -1, rightBound = -1;
        int mid;
        while (low <= high) {
            mid = (low + high) >>> 1;
            if (nums[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        // 这个边界问题容易写错
        rightBound = high;
        low = 0;
        high = nums.length - 1;
        while (low <= high) {
            mid = (low + high) >>> 1;
            if (nums[mid] >= target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        // 容易出问题地方
        leftBound = low;
        return rightBound - leftBound + 1;
    }
}
