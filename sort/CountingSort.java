import java.util.ArrayList;
import java.util.List;

/**
 * 计数排序
 * 稳定的线性排序，复杂度是O(n)
 * 计数排序是一种稳定的线性时间排序算法。计数排序使用一个额外的数组C
 * 计数排序不是比较排序，排序的速度快于任何比较排序算法
 * 缺点也很明显，数组必须为有界的，并且数组范围最好不要太大，不然很耗费内存，当然它可以应用在基数排序中的排序数据范围较大的数组
 */
public class CountingSort {
    public static void sort(Integer[] sequenceForArray) {
        int maxValue = sequenceForArray[0], minValue = sequenceForArray[0];
        // 1. 获取最大和最小值， 用于创建临时的计数数组
        for (int i = 1; i < sequenceForArray.length; i++) {
            if (sequenceForArray[i] < minValue) {
                minValue = sequenceForArray[i];
            }
            if (sequenceForArray[i] > maxValue) {
                maxValue = sequenceForArray[i];
            }
        }
        // 2. 创建计数数组
        int[] countArray = new int[maxValue - minValue + 1];
        // 3. 迭代待排序数组，做计数操作，这个时候表示的是每一个距离最小值的数在待排序序列中有几次
        for (int i = 0; i < sequenceForArray.length; i++) {
            int value = sequenceForArray[i];
            countArray[value - minValue] += 1;
        }
        // 4. 依次迭代，计算每个值应当所在位置
        Integer[] tempArray = new Integer[sequenceForArray.length];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = sequenceForArray[i];
        }
        // 5. 做叠加操作
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i - 1];
        }
        // 整理设置
        for (int i = tempArray.length - 1; i >= 0; i--) {
            int value = tempArray[i];
            sequenceForArray[countArray[value - minValue] - 1] = value;
            countArray[value - minValue] -= 1;
        }
    }

    public static void sort(int[] sequenceForArray) {
        int maxValue = sequenceForArray[0], minValue = sequenceForArray[0];
        // 获取最大和最小值
        for (int i = 1; i < sequenceForArray.length; i++) {
            if (sequenceForArray[i] < minValue) {
                minValue = sequenceForArray[i];
            }
            if (sequenceForArray[i] > maxValue) {
                maxValue = sequenceForArray[i];
            }
        }
        int[] countArray = new int[maxValue - minValue + 1];
        // 计数
        for (int i = 0; i < sequenceForArray.length; i++) {
            int value = sequenceForArray[i];
            countArray[value - minValue] += 1;
        }
        // 计算每个值所在位置
        Integer[] tempArray = new Integer[sequenceForArray.length];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = sequenceForArray[i];
        }
        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i - 1];
        }
        // 整理设置
        for (int i = tempArray.length - 1; i >= 0; i--) {
            int value = tempArray[i];
            sequenceForArray[countArray[value - minValue] - 1] = value;
            countArray[value - minValue] -= 1;
        }
        for (int i = 0; i < sequenceForArray.length; i++) {
            System.out.println(sequenceForArray[i]);
        }
    }

    public static void sort(List<Integer> sequenceForArray) {
        int maxValue = sequenceForArray.get(0), minValue = sequenceForArray.get(0);
        // 获取最大和最小值
        for (int i = 1; i < sequenceForArray.size(); i++) {
            if (sequenceForArray.get(i) < minValue) {
                minValue = sequenceForArray.get(i);
            }
            if (sequenceForArray.get(i) > maxValue) {
                maxValue = sequenceForArray.get(i);
            }
        }
        int[] countArray = new int[maxValue - minValue + 1];
        // 计数
        for (int i = 0; i < sequenceForArray.size(); i++) {
            int value = sequenceForArray.get(i);
            countArray[value - minValue] += 1;
        }
        // 计算每个值所在位置

        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i - 1];
        }
        List<Integer> tempArray = new ArrayList<Integer>();
        for (int i = 0; i < tempArray.size(); i++) {
            tempArray.add(sequenceForArray.get(i));
        }
        // 整理设置
        for (int i = tempArray.size() - 1; i >= 0; i--) {
            int value = tempArray.get(i);
            sequenceForArray.set(countArray[value - minValue] - 1, value);
            countArray[value - minValue] -= 1;
        }
    }

    public static void main(String[] args) {
        int[] ints = {4, 5, 6, 3, 2, 1, 8, 9, 3, 16, 8, 23, 6, 123, 43, 54, 678, 8, 6, 32, 33};
        sort(ints);
    }
}
