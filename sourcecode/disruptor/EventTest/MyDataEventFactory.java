package disruptor.EventTest;

import com.lmax.disruptor.EventFactory;

/**
 * @author fourous
 * @date: 2020/4/21
 * @description:
 */
public class MyDataEventFactory implements EventFactory<MyDataEvent> {
    @Override
    public MyDataEvent newInstance() {
        return new MyDataEvent();
    }
}
