package tongge;

import java.util.concurrent.Phaser;
import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/3/27
 * @description: Phaser测试历程
 */
public class PhaserTest {
    public static final int PARTIES = 3;
    public static final int PHASERS = 4;

    public static void main(String[] args) {
        Phaser phaser = new Phaser(PARTIES) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("================phase " + phase + "=================");
                return super.onAdvance(phase, registeredParties);
            }
        };
        IntStream.range(0, PARTIES).forEach(i -> new Thread(() -> {
            IntStream.range(0, PHASERS).forEach(j -> {
                System.out.println(String.format("%s: phase %d", Thread.currentThread().getName(), j));
                phaser.arriveAndAwaitAdvance();
            });
        },"Thread "+i).start());
    }
}
