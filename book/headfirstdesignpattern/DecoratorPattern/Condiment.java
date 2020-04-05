package headfirstdesignpattern.DecoratorPattern;

/**
 * Created by ZhiHui Liu on 2020/4/5.
 * 调料抽象类，也就是装饰者类
 */
public abstract class Condiment extends Beverage {
    @Override
    public abstract String getDescription();
}
