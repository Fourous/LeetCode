package headfirstdesignpattern.DecoratorPattern;

/**
 * Created by ZhiHui Liu on 2020/4/5.
 */
public class TallSize extends CoffeeSize{
    Beverage beverage;

    public TallSize(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ",tallSize";
    }

    @Override
    public double cost() {
        return .10 + beverage.cost();
    }
}
