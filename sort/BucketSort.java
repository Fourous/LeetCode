import java.util.Arrays;

/**
 * @author fourous
 * @date: 2020/3/8
 * @description: 桶排序是计数排序的升级版。它利用了函数的映射关系，高效与否的关键就在于这个映射函数的确定。为了使桶排序更加高效，我们需要做到这两点：
 * 1:在额外空间充足的情况下，尽量增大桶的数量
 * 2:使用的映射函数能够将输入的 N 个数据均匀的分配到 K 个桶中
 * 同时，对于桶中元素的排序，选择何种比较排序算法对于性能的影响至关重要,当输入的数据均匀分布在桶中，是最快的，当数据分布在少数桶甚至一个桶里面是最慢的
 * 对于桶排序是否是稳定的，这个取决于在桶中使用的排序算法，桶排序是需要结合其他排序算法使用的
 */
public class BucketSort {
    /**
     * 桶的大小指定也是和效率有关
     * 其根据还是根据给定的数组范围来定
     */
    public static int BUCKETSIZE = 5;

    public static void bucketSort(int[] intArray) {
        if (intArray.length == 0) {
            return;
        }
        int max = intArray[0];
        int min = intArray[0];
        // 获取最大最小值
        for (int value : intArray) {
            if (value < min) {
                min = value;
            } else if (value > max) {
                max = value;
            }
        }
        int bucketCount = ((max - min) / BUCKETSIZE) + 1;
        int[][] buckets = new int[bucketCount][0];
        // 函数映射到桶中
        for (int i = 0; i < intArray.length; i++) {
            int index = (intArray[i] - min) / BUCKETSIZE;
            buckets[index] = arrAppend(buckets[index], intArray[i]);
        }
        int arrIndex = 0;
        for (int[] bucket : buckets) {
            if (bucket.length <= 0) {
                continue;
            }
            // 这里使用的是冒泡排序
            BubbleSort.bubbleSort(bucket);
            for (int value : bucket) {
                intArray[arrIndex++] = value;
            }
        }
    }

    /**
     * 添加数据，自动扩容
     *
     * @param intArray
     * @param value
     * @return
     */
    public static int[] arrAppend(int[] intArray, int value) {
        intArray = Arrays.copyOf(intArray, intArray.length + 1);
        intArray[intArray.length - 1] = value;
        return intArray;
    }

    public static void main(String[] args) {
        int[] ints = {4, 5, 6, 3, 2, 1, 8, 9, 3, 16, 8, 23, 6, 123, 43, 54, 678, 8, 6, 32, 33};
        bucketSort(ints);
    }
}
