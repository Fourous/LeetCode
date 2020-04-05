package headfirstdesignpattern.CommandPattern;

/**
 * Created by ZhiHui Liu on 2020/4/5.
 */
public class SimpleRemoteControl {
    Command slot;

    public SimpleRemoteControl() {
    }

    public void setCommand(Command command) {
        slot = command;
    }

    public void buttonWasPressed() {
        slot.execute();
    }
}
