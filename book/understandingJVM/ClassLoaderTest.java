package understandingJVM;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author fourous
 * @date: 2020/3/30
 * @description: 对于类对象的equals isAssignableFrom instance方法的返回结果，包括使用instance关键字做所属对象的判定情况
 * 如果不注意类加载器的影响，某些情况是不准确的
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object object = classLoader.loadClass("understandingJVM.ClassLoaderTest").newInstance();
        System.out.println(object.getClass());
        System.out.println(object instanceof understandingJVM.ClassLoaderTest);
    }
}
