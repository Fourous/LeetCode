package headfirstdesignpattern.DecoratorPattern;

/**
 * Created by ZhiHui Liu on 2020/4/5.
 */
public class DarkRoast extends Beverage {
    public DarkRoast() {
        description = "Espresso";
    }

    @Override
    public double cost() {
        return 1.99;
    }
}
