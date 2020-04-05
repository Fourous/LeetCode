package headfirstdesignpattern.DecoratorPattern;

/**
 * Created by ZhiHui Liu on 2020/4/5.
 * 实现具体装饰者
 */
public class Mocha extends Condiment {
    Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",Mocha";
    }

    @Override
    public double cost() {
        return .20 + beverage.cost();
    }
}
