package understandingJVM;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * @author fourous
 * @date: 2020/3/31
 * @description: 使用MethodHandle解决子类调用祖类方法
 * 这里好像和书中不符，只能看到调用了father
 * 这里JDK7和JDK8由于JSR 修改过，是不能直接跳过上层调用更加上层的虚方法的
 * <p>
 * findStatic	invokestatic	调用静态方法
 * findSpecial	invokespecial	调用实例构造方法，私有方法，父类方法。
 * findVirtual	invokevirtual	调用所有的虚方法
 * findVirtual	invokeinterface	调用接口方法，会在运行时再确定一个实现此接口的对象。
 *
 * 单独对Java而言，不如反射包
 */
public class MethodHandleProblem {
    class GrandFather {
        void thinking() {
            System.out.println("I am grandfather");
        }
    }

    class Father extends GrandFather {
        /**
         * 这里注释掉直接父类方法，是可以继续查找父类的
         */
//        @Override
//        void thinking() {
//            System.out.println("I am father");
//        }
    }

    class Son extends Father {
        @Override
        void thinking() {
            try {
                MethodType mt = MethodType.methodType(void.class);
                /**
                 * 这里原书是调用的GrandFather
                 * 注释father的方法后，去查找他，查找不到会在向上找GrandFather方法
                 * 很像双亲委派机制反过来的感觉，从底开始找，找不到向上找
                 */
                MethodHandle mh = lookup().findSpecial(Father.class, "thinking", mt, getClass());
                mh.invoke(this);
            } catch (Throwable e) {

            }
        }
    }

    public static void main(String[] args) {
        (new MethodHandleProblem().new Son()).thinking();
    }
}
