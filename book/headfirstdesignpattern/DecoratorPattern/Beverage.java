package headfirstdesignpattern.DecoratorPattern;

/**
 * Created by ZhiHui Liu on 2020/4/5.
 * 装饰者模式抽象类抽象组件
 */
public abstract class Beverage {
    String description = "Unknown Beverage";
    String size = "unknown size";

    public String getDescription() {
        return description;
    }

    public abstract double cost();

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
