package disruptor.EventTest;

import com.lmax.disruptor.EventTranslatorTwoArg;

/**
 * @author fourous
 * @date: 2020/4/21
 * @description:
 */
public class MyDataEventTranslatorWithIdAndValue implements EventTranslatorTwoArg<MyDataEvent, Integer, String> {
    @Override
    public void translateTo(MyDataEvent event, long sequence, Integer id,
                            String value) {
        MyData data = new MyData(id, value);
        event.setMyData(data);
    }
}
