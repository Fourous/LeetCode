package jdk8;

import java.util.concurrent.CompletableFuture;

/**
 * @author fourous
 * @date: 2020/4/17
 * @description: CompletableFuture使用历程
 * JDK8 引入改进Future主线程等待问题，可以传入一个回调对象，当线程执行完毕，默认执行回调方法
 * 执行异步处理流程
 * thenAccept()处理正常结果
 * exceptional()处理异常结果
 * thenApplyAsync()用于串行化另一个CompletableFuture
 * anyOf() 任何一个成功，和allOf() 都成功处理多个Future
 */
public class CompletableFutureTest {
    public static void main(String[] args) throws Exception {

        CompletableFuture<Double> completableFuture = CompletableFuture.supplyAsync(CompletableFutureTest::fetchPrice);
        System.out.println("complete is running");
        completableFuture.thenAccept((result) -> {
            System.out.println("price is " + result);
        });

        completableFuture.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });
        // 证明确实是异步执行，运行了main以后，才返回price
        System.out.println("main is running");
        Thread.sleep(2000);
    }

    static double fetchPrice() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price error");
        }
        return 5 + Math.random() * 20;
    }
}
