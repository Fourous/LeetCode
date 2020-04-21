package disruptor.processor;

import com.lmax.disruptor.EventHandler;
import disruptor.EventTest.MyDataEvent;

import java.util.concurrent.TimeUnit;

/**
 * @author fourous
 * @date: 2020/4/21
 * @description: 定义处理方法
 */
public class MyDataEventHandler implements EventHandler<MyDataEvent> {
    @Override
    public void onEvent(MyDataEvent event, long sequence, boolean endOfBatch) throws Exception {
        //注意这里小睡眠了一下!!
        TimeUnit.SECONDS.sleep(3);
        System.out.println("handle event's data:" + event.getMyData() + "isEndOfBatch:" + endOfBatch);
    }
}
