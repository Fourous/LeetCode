package headfirstdesignpattern.CommandPattern;

import java.util.stream.IntStream;

/**
 * Created by ZhiHui Liu on 2020/4/5.
 */
public class RemoteControl {
    Command[] onCommands;
    Command[] offCommands;
    /**
     * 记录上次按下的按钮
     */
    Command undoCommand;

    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];
        Command noCommand = new NoCommand();
        IntStream.range(0, 7).forEach(i -> {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        });
        undoCommand = noCommand;
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonPushed(int slot) {
        offCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void undoButtonWasPushed() {
        undoCommand.undo();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n------------ Remote Control -------------\n");
        IntStream.range(0, onCommands.length).forEach(i -> {
            stringBuffer.append("[slot" + i + "]" + onCommands[i].getClass().getName() + "       " + offCommands[i].getClass().getName() + "\n");
        });
        return stringBuffer.toString();
    }
}
