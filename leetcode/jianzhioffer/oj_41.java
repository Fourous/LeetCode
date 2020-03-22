package jianzhioffer;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * @author fourous
 * @date: 2020/3/22
 * @description: 数据流的中位数
 * 这题难度为困难，如果是直接排序方法做，其实就不难了，就是优化空间上面，需要更大的优化
 * 本题直接采用大小堆进行计算，对于这种中位数的OJ，基本都可以用大小堆来实现
 */
public class oj_41 {
    /**
     * 直接用最大堆存储大于小于中位数的数字，用最小堆存储小于中位数的数字
     * 为了保持平衡，最大堆要么和最小堆个数相同要么比最小堆多一个
     * 适用堆计算中位数只需要O(1),排序就需要log(n)
     */
    public PriorityQueue<Integer> minHeap, maxHeap;

    public oj_41() {
        minHeap = new PriorityQueue<>();
        /**
         * 注意Java中优先队列默认的最小堆，我们需要把它转化为最大堆，可以适用Collections工具类，或者适用lambada
         * maxHeap = new PriorityQueue<>(Collections.reverseOrder())
         */
        maxHeap = new PriorityQueue<>((v1, v2) -> v2 - v1);
    }

    public void addNum(int num) {
        maxHeap.offer(num);
        minHeap.offer(maxHeap.poll());
        if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if (minHeap.size() == maxHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) * 0.5;
        } else {
            return maxHeap.peek();
        }
    }
    /**
     * 这题还可以适用二叉查找插入的方式，事先将元素插入排好序，复杂度O(n)
     *  当然在得到中位数时候需要计算索引，偶数计算中间两位，奇数计算中一位
     *  判断可以用n%2==0 或者 位运算 n&1==1来判断
     */
}
