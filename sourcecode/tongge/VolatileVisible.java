package tongge;

/**
 * @author fourous
 * @date: 2020/3/26
 * @description: volatile可见行历程
 */
public class VolatileVisible {
    public static volatile int finish = 0;

    /**
     * 这里其实还是会停的
     */
    public static void checkFinished() {
        int count =0;
        while (finish == 0) {
            count++;
            System.out.println("do something");
        }
        System.out.println("finished and count is"+count);
    }

    public static void finish() {
        finish = 1;
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> checkFinished()).start();
        Thread.sleep(100);
        finish();
        System.out.println("main finished");
    }
}
