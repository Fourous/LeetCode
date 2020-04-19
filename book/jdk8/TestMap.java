package jdk8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/4/17
 * @description: 测试JDK 8 新功能
 * 添加了一个新的抽象
 * +--------------------+       +------+   +------+   +---+   +-------+
 * | stream of elements +-----> |filter+-> |sorted+-> |map+-> |collect|
 * +--------------------+       +------+   +------+   +---+   +-------+
 * 数据源：数组，集合，I/O channel,产生器generator
 * 聚合操作：类似SQL操作，foreach,map,filter,limit,sorted,collectors,SummaryStatistics
 */
public class TestMap {
    public static void main(String[] args) {
        // 数组产生流
        int[] arrList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] newArray = Arrays.stream(arrList)
                .filter(a -> (a > 3 && a < 8))
                .map(b -> b * 2)
                .toArray();
        System.out.println("****** 数组产生流 ********");
        for (int i = 0; i < newArray.length; i++) {
            System.out.println(newArray[i]);
        }
        // 集合产生流
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Object[] newList = arrayList.stream()
                .filter(a -> (a > 3 && a < 7))
                .map(b -> b * b)
                .toArray();
        System.out.println("********* 集合产生流 **********");
        System.out.println(newList[1]);

        List<String> listStrings = Arrays.asList("abc", "cde", "fa", "", "kk", "fff");
        List<String> newStrings = listStrings.stream()
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toList());
        Iterator itr = newStrings.iterator();
        System.out.println("********* 测试collect *********");
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        System.out.println("******** 测试limit功能 **********");
        Random random = new Random();
        random.ints()
                .limit(10)
                .forEach(System.out::println);

        System.out.println("********* 测试map功能 *********");
        List<Integer> listNum = Arrays.asList(3, 4, 6, 1, 7, 4, 2, 6, 9, 2);
        List<Integer> newListNum = listNum.stream()
                .map(i -> i * i)
                // 去重操作
                .distinct()
                .collect(Collectors.toList());
        IntStream.range(0, newListNum.size()).forEach(i -> {
            System.out.println(newListNum.get(i));
        });


        System.out.println("********* 测试Sorted功能 *******");
        Random random1 = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);


        System.out.println("********* 测试并行 **************");
        /**
         * 多次测试发现，并行不如直接Stream
         * 所以程序简单最好不要用并行，反而浪费
         * 这里使用了大量历程测试并行差异，但是好像并行表现都比较差
         * 网上查阅资料，发现并行流是通过Fork/Join方式实现的，所以导致性能影响比较大，简单任务最好不要随便执行此
         * 1：是否需要并行
         * 2：人物之间是否独立，是否会引起竞态条件
         * 3：结果是否取决于任务的顺序
         */
        List<String> strings = Arrays.asList("abc", "fde", "", "df", "", "fj");
        int[] ints1 = new Random()
                .ints()
                .limit(90000000)
                .toArray();
        long start1 = System.currentTimeMillis();
        strings.parallelStream().filter(string -> !string.isEmpty()).count();
        Arrays.stream(ints1)
                .sorted()
                .map(x -> x * x)
                .distinct();
        System.out.println("parallel time is " + (System.currentTimeMillis() - start1));

        int[] ints2 = new Random()
                .ints()
                .limit(90000000)
                .toArray();
        long start2 = System.currentTimeMillis();
        Arrays.stream(ints2)
                .sorted()
                .map(x -> x * x)
                .distinct();
        strings.stream().filter(string -> !string.isEmpty()).count();
        System.out.println("stream time is " + (System.currentTimeMillis() - start2));


        System.out.println("********* 测试Collectors ********");
        List<String> strings1 = Arrays.asList("abc", "fde", "", "df", "", "fj");
        String collectString = strings1.parallelStream()
                .filter(string -> !string.isEmpty())
                .collect(Collectors.joining(","));
        System.out.println("collects is " + collectString);

        System.out.println("******** 统计 *************");
        // 1，2，3，4，6，9
        List<Integer> integerList = Arrays.asList(1, 3, 4, 2, 6, 9, 6, 2, 4, 6);
        IntSummaryStatistics statistics = integerList.stream()
                // 这一步是必须的
                .mapToInt((x) -> x)
                .distinct()
                .summaryStatistics();
        System.out.println("count " + statistics.getCount());
        System.out.println("max " + statistics.getMax());
        System.out.println("average " + statistics.getAverage());
        System.out.println("min " + statistics.getMin());
        System.out.println("sum " + statistics.getSum());
    }


}
