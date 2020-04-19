package jdk8;

import java.util.concurrent.CompletableFuture;

/**
 * @author fourous
 * @date: 2020/4/19
 * @description:CompletableFuture串行执行
 */
public class CompletableFutureSerial {
    public static void main(String[] args) throws Exception {
        // 首先执行查询查询代码任务
        CompletableFuture<String> completableFutureQuery = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油");
        });
        completableFutureQuery.exceptionally((e) -> {
            e.printStackTrace();
            System.out.println("查询代码失败");
            return null;
        });
        // 然后执行查询价格任务
        CompletableFuture<Double> completableFuturePrice = completableFutureQuery.thenApplyAsync((code) -> {
            return fetchPrice(code);
        });
        completableFuturePrice.thenAccept((result) -> {
            System.out.println("price is " + result);
        });
        // 这里主线程不要立即关闭，不然默认线程池会直接关闭
        Thread.sleep(2000);
    }

    static String queryCode(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("查询代码失败");
        }
        return "601857";
    }

    static Double fetchPrice(String code) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Math.random() * 20 + 10;
    }
}
