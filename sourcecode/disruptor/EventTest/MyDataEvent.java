package disruptor.EventTest;

/**
 * @author fourous
 * @date: 2020/4/21
 * @description:
 */
public class MyDataEvent {
    private MyData myData;

    public MyData getMyData(){
        return myData;
    }
    public void setMyData(MyData myData){
        this.myData = myData;
    }
}
