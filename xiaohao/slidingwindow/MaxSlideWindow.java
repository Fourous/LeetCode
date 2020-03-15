package slidingwindow;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author fourous
 * @date: 2020/3/14
 * @description: 滑动窗口最大值问题
 * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
 * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。返回滑动窗口中的最大值。
 */
public class MaxSlideWindow {
    /**
     * 最简单的做法就是遍历数组，把所以情况列出来
     * 复杂度达到O(LK)
     *
     * @param num
     * @param k
     */
    public static int[] slideWindow1(int[] num, int k) {
        int len = num.length;
        if (len * k == 0) {
            return new int[0];
        }
        int[] win = new int[len - k + 1];
        for (int i = 0; i < len - k + 1; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, num[j]);
            }
            win[i] = max;
        }
        return win;
    }

    /**
     * 单调队列
     * 这里单调队列和优先队列思想差不多，在双端队列中，存储数组下标值，并且我们维持从大到小的顺序排列
     * 如果数据进来，尾部进来以后，如果比这个数小，弹出比他小的数据
     * 然后继续判断，是否前面的数在窗口里面，不在就直接弹出
     * 这两个条件满足后，这个队首的数据就是当前窗口的最大值
     * 这个算法复杂度可以直接达到O(n)
     *
     * @param num
     * @param k
     */
    public static int[] slideWindow2(int[] num, int k) {
        if (num == null || num.length < 2) {
            return num;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        int[] result = new int[num.length - k + 1];
        for (int i = 0; i < num.length; i++) {
            while (!queue.isEmpty() && num[queue.peekLast()] <= num[i]) {
                queue.pollLast();
            }
            // 加入当前数下标，存下标容易判断是否在窗口里面
            queue.addLast(i);
            // 这里实际只需要判断对首元素是否在窗口就够了
            if (queue.peek() <= i - k) {
                queue.poll();
            }
            if (i + 1 >= k) {
                result[i + 1 - k] = num[queue.peek()];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {8, 7, 6, 5, 4, 3, 2, 1};
        System.out.println(Arrays.toString(slideWindow1(nums, 3)));
        System.out.println(Arrays.toString(slideWindow2(nums, 3)));
    }
}
