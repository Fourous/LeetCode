package headfirstdesignpattern.CommandPattern;

/**
 * Created by ZhiHui Liu on 2020/4/5.
 */
public interface Command {
    public void execute();

    public void undo();
}
