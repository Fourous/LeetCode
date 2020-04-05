package headfirstdesignpattern.CommandPattern;

/**
 * Created by ZhiHui Liu on 2020/4/5.
 */
public class RemoteControllTest {
    public static void main(String[] args) {
        /**
         * 简单打开开关
         */
        SimpleRemoteControl remote = new SimpleRemoteControl();
        Light light = new Light();
        LightOnCommand lightOn = new LightOnCommand(light);
        remote.setCommand(lightOn);
        remote.buttonWasPressed();
        /**
         * 创建插槽
         */
        RemoteControl remoteControl = new RemoteControl();
        Light livingRoom = new Light();
        LightOnCommand lightOnCommand = new LightOnCommand(livingRoom);
        LightOffCommand lightOffCommand = new LightOffCommand(livingRoom);
        remoteControl.setCommand(0,lightOnCommand,lightOffCommand);
        System.out.println(remoteControl);
        remoteControl.onButtonWasPushed(0);
        remoteControl.offButtonPushed(0);
        remoteControl.undoButtonWasPushed();
        remoteControl.onButtonWasPushed(2);
        remoteControl.onButtonWasPushed(3);
        remoteControl.onButtonWasPushed(4);
        remoteControl.onButtonWasPushed(5);
        remoteControl.onButtonWasPushed(6);
    }
}
