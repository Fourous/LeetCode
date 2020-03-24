package tongge;

import java.io.Serializable;

/**
 * @author fourous
 * @date: 2020/3/24
 * @description: 多种实例化方式
 */
public class UserTestForUnsafe implements Cloneable, Serializable {
    public int age;

    public UserTestForUnsafe() {
        age = 10;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
