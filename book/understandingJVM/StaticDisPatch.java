package understandingJVM;

import java.io.Serializable;

/**
 * @author fourous
 * @date: 2020/3/30
 * @description: 方法静态分派历程
 */
public class StaticDisPatch {
    Character a =0;
    static abstract class Human {

    }

    static class Man extends Human {

    }

    static class Women extends Human {

    }

    public void sayHello(Human guy) {
        System.out.println("hello,guy!");
    }

    public void sayHello(Man guy) {
        System.out.println("hello,gentleman!");
    }

    public void sayHello(Women guy) {
        System.out.println("hello,lady!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Women();
        StaticDisPatch sr = new StaticDisPatch();
        sr.sayHello(man);
        sr.sayHello(women);
    }
}
