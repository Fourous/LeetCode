package understandingJVM;

/**
 * @author fourous
 * @date: 2020/3/30
 * @description: 字段解析
 */
public class DeadLoopClass {
    static {
        /**
         * 不加这个if编译器会直接提示"initializer does not complete normally
         * 这里也看得出来，Idea确实会将代码直接编译解析
         */
        if (true) {
            System.out.println(Thread.currentThread() + "init DeadLoopClass");
            while (true) {

            }
        }
    }

    public static void main(String[] args) {

        Runnable script = () -> {
            System.out.println(Thread.currentThread() + "start");
            DeadLoopClass dlc = new DeadLoopClass();
            System.out.println(Thread.currentThread() + "run over");
        };

        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}
