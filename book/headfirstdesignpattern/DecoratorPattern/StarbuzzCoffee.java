package headfirstdesignpattern.DecoratorPattern;

/**
 * Created by ZhiHui Liu on 2020/4/5.
 */
public class StarbuzzCoffee {
    public static void main(String[] args) {
        /**
         * 一杯HouseBlend咖啡，加摩卡加牛奶
         * 来个小杯
         */
        Beverage beverage = new HouseBlend();
        beverage = new Mocha(beverage);
        beverage = new Whip(beverage);
        beverage = new TallSize(beverage);
        System.out.println(beverage.getDescription() + "$" + beverage.cost());
    }
}
