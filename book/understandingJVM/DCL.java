package understandingJVM;

/**
 * @author fourous
 * @date: 2020/3/31
 * @description: DCL单例模式，注意在JDK1.5以下，应为Volatile没有完全消除重排序问题，导致单例模式存在问题
 * Java高级编程里面指出，最好采用静态内部类
 */
public class DCL {
    private volatile static DCL instance;

    public static DCL getInstance() {
        if (instance == null) {
            synchronized (DCL.class) {
                if (instance == null) {
                    instance = new DCL();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        DCL.getInstance();
    }
}
