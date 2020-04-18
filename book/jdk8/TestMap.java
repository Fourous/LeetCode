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
    }


}
