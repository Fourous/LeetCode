package disruptor.EventTest;

import com.lmax.disruptor.EventTranslator;

/**
 * @author fourous
 * @date: 2020/4/21
 * @description: 事件转换器，disruptor里面还有其他的事件转换器
 */
public class MyDataEventTranslator implements EventTranslator<MyDataEvent> {
    @Override
    public void translateTo(MyDataEvent event, long sequence) {
        //新建一个数据
        MyData data = new MyData(1, "holy shit!");
        //将数据放入事件中。
        event.setMyData(data);
    }
}
