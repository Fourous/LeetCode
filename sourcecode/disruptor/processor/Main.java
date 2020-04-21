package disruptor.processor;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import disruptor.EventTest.MyDataEvent;
import disruptor.EventTest.MyDataEventFactory;
import disruptor.EventTest.MyDataEventTranslatorWithIdAndValue;

import java.io.IOException;

/**
 * @author fourous
 * @date: 2020/4/21
 * @description:
 */
public class Main {
    public static void main(String[] args) {
        // 创建一个RingBuffer，容量为4
        RingBuffer<MyDataEvent> ringBuffer = RingBuffer.createSingleProducer(new MyDataEventFactory(), 4);
        // 创建一个事件处理器
        BatchEventProcessor<MyDataEvent> batchEventProcessor =
                new BatchEventProcessor<>(ringBuffer, ringBuffer.newBarrier(), new MyDataEventHandler());
        // 将事件处理器本身的序列设置为ringBuffer的追踪序列
        // 这一步将追踪处理序列，根据处理序列情况来判断是否等待
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
        // 启动事件处理器
        new Thread(batchEventProcessor).start();
        for (int i = 0; i < 10; i++) {
            ringBuffer.publishEvent(new MyDataEventTranslatorWithIdAndValue(), i, i + "s");
            System.out.println("发布事件[" + i + "]");
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
