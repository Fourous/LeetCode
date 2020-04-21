package disruptor.EventTest;

import com.lmax.disruptor.RingBuffer;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @author fourous
 * @date: 2020/4/21
 * @description: 1.定义好要生产的数据和相应的事件类(里面存放数据)。
 * 2.定于好事件转换器或者直接用RingBuffer进行事件发布。
 * 3.明确发布场景，合理的选择发布模式(单线程还是多线程)
 */
public class Main {
    public static void main(String[] args) {
        /**
         * 利用转换器发布事件
         */
        RingBuffer<MyDataEvent> ringBuffer = RingBuffer.createSingleProducer(new MyDataEventFactory(), 1024);
        ringBuffer.publishEvent(new MyDataEventTranslator());

        /**
         * 直接利用RingBuffer发布事件
         */
        long sequence = ringBuffer.next();
        try {
            MyDataEvent dataEvent = ringBuffer.get(sequence);
            MyData myData = new MyData(2, "hello world");
            dataEvent.setMyData(myData);
        } finally {
            ringBuffer.publish(sequence);
        }

        MyDataEvent myDataEvent = ringBuffer.get(1);
        System.out.println("Event: " + myDataEvent);
        System.out.println("Data: " + myDataEvent.getMyData());

        /**
         * 多线程发布事件
         */
        final RingBuffer<MyDataEvent> multiRingBuffer = RingBuffer.createMultiProducer(new MyDataEventFactory(), 1024);
        final CountDownLatch countDownLatch = new CountDownLatch(100);
        IntStream.range(0, 100).forEach(i -> {
            new Thread(() -> {
                long seq = ringBuffer.next();
                try {
                    MyDataEvent myDataEvent1 = multiRingBuffer.get(seq);
                    MyData data = new MyData(i, "index" + i);
                    myDataEvent1.setMyData(data);
                } finally {
                    multiRingBuffer.publish(seq);
                    countDownLatch.countDown();
                }
            });
        });

        try {
            countDownLatch.await();
            IntStream.range(0, 105).forEach(i -> {
                MyDataEvent event = multiRingBuffer.get(i);
                System.out.println("multi event" + event.getMyData());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
