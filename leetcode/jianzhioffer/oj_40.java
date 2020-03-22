package jianzhioffer;

import java.util.*;

/**
 * @author fourous
 * @date: 2020/3/22
 * @description: 最小K问题, 也就是TopK 问题，如果直接快排的话复杂度会到nlogn复杂度，也是最简单的方式，但是其实改装一下可以达到O(n)
 */
public class oj_40 {
    /**
     * 快速排序
     *
     * @param arr
     * @param start
     * @param end
     */
    public static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int index = partition(arr, start, end);
            quickSort(arr, start, index - 1);
            quickSort(arr, index + 1, end);
        }
    }

    /**
     * 改装后的快排，也就是不需要整个排序，找到划分点为k时候，直接返回0-k的数组即可
     * 这里K是指当前下标
     *
     * @param arr
     * @param start
     * @param end
     * @param k
     * @return
     */
    public static int[] quickSortRefit(int[] arr, int start, int end, int k) {
        if (arr.length == 0 || k == 0) {
            return new int[0];
        }
        int index = partition(arr, start, end);
        if (index == k) {
            return Arrays.copyOf(arr, k + 1);
        }
        return index > k ? quickSortRefit(arr, start, index - 1, k) : quickSortRefit(arr, index + 1, end, k);
    }

    /**
     * 分割字符串
     *
     * @param arr
     * @param start
     * @param end
     * @return
     */
    public static int partition(int[] arr, int start, int end) {
        int base = arr[start];
        int i = start;
        int j = end;
        while (i < j) {
            while (i < j && arr[j] >= base) {
                j--;
            }
            while (i < j && arr[i] <= base) {
                i++;
            }
            swap(arr, i, j);
        }
        swap(arr, start, i);
        return i;
    }

    /**
     * 交换
     *
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 采用最小堆求解，复杂度可达到O(NlogK)
     * 这里注意，要用大顶堆来实现找K小，小顶堆的话复杂度会达到NlogN
     *
     * @param arr
     * @param k
     * @return
     */
    public static Integer[] getKByPriority(int[] arr, int k) {
        if (arr.length == 0 || k == 0) {
            return new Integer[0];
        }
        // java中默认的堆是小顶堆，这里改成大顶堆
        Queue<Integer> queue = new PriorityQueue<>((v1, v2) -> v2 - v1);
        /**
         * 遍历数组，如果堆中个数小于K的话，就直接入堆
         * 如果大于K首先判断当然数字是否大于堆顶，大于的话直接略过，小于的话，把堆顶弹出，然后入堆
         */
        for (int num : arr) {
            if (queue.size() < k) {
                queue.offer(num);
            } else if (num < queue.peek()) {
                queue.poll();
                queue.offer(num);
            }
        }
        // 当然这里要是返回int类型就得循环赋值了，这里直接用包装器来返回Integer数组
        return queue.toArray(new Integer[queue.size()]);
    }

    /**
     * 依据题意，这里数组是有限的，并且数字的大小是正树
     * 我们可以用计数排序来做，用空间换时间
     * 复杂度为O(N）
     *
     * @param arr
     * @param k
     * @return
     */
    public static int[] getMinByCountingSort(int[] arr, int k) {
        if (k == 0 || arr.length == 0) {
            return new int[0];
        }
        // 统计每个数字出现的次数
        int[] counter = new int[10001];
        for (int num : arr) {
            counter[num]++;
        }
        // 根据counter数组从头找出k个数作为返回结果
        int[] res = new int[k];
        int idx = 0;
        for (int num = 0; num < counter.length; num++) {
            while (counter[num]-- > 0 && idx < k) {
                res[idx++] = num;
            }
            if (idx == k) {
                break;
            }
        }
        return res;
    }

    /**
     * BST 二叉搜索树也可以适用这题，只是不太常见
     * TreeMap的key是数字，value是该数字的个数。
     * 我们遍历数组中的数字，维护一个数字总个数为K的TreeMap：
     * 1.若目前map中数字个数小于K，则将map中当前数字对应的个数+1；
     * 2.否则，判断当前数字与map中最大的数字的大小关系：若当前数字大于等于map中的最大数字，就直接跳过该数字；
     * 若当前数字小于map中的最大数字，则将map中当前数字对应的个数+1，并将map中最大数字对应的个数减1.
     *
     * @param arr
     * @param k
     */
    public static int[] getMinByBST(int[] arr, int k) {
        if (k == 0 || arr.length == 0) {
            return new int[0];
        }
        // TreeMap的key是数字, value是该数字的个数。
        // cnt表示当前map总共存了多少个数字。
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int cnt = 0;
        for (int num : arr) {
            // 1. 遍历数组，若当前map中的数字个数小于k，则map中当前数字对应个数+1
            if (cnt < k) {
                map.put(num, map.getOrDefault(num, 0) + 1);
                cnt++;
                continue;
            }
            // 2. 否则，取出map中最大的Key（即最大的数字), 判断当前数字与map中最大数字的大小关系：
            //    若当前数字比map中最大的数字还大，就直接忽略；
            //    若当前数字比map中最大的数字小，则将当前数字加入map中，并将map中的最大数字的个数-1。
            Map.Entry<Integer, Integer> entry = map.lastEntry();
            if (entry.getKey() > num) {
                map.put(num, map.getOrDefault(num, 0) + 1);
                if (entry.getValue() == 1) {
                    map.pollLastEntry();
                } else {
                    map.put(entry.getKey(), entry.getValue() - 1);
                }
            }

        }

        // 最后返回map中的元素
        int[] res = new int[k];
        int idx = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int freq = entry.getValue();
            while (freq-- > 0) {
                res[idx++] = entry.getKey();
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {3, 4, 2, 5, 0, 8, 6, 8, 7, 3, 2, 1, 6, 4, 2};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(Arrays.copyOf(arr, 6)));
        // 这里注意K是下标
        System.out.println(Arrays.toString(quickSortRefit(arr, 0, arr.length - 1, 2)));
        System.out.println(Arrays.toString(getKByPriority(arr, 6)));
        System.out.println(Arrays.toString(getMinByBST(arr, 6)));
        System.out.println(Arrays.toString(getMinByCountingSort(arr, 6)));
    }
}
