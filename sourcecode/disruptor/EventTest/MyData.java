package disruptor.EventTest;

/**
 * @author fourous
 * @date: 2020/4/21
 * @description:
 */
public class MyData {
    private int id;
    private String value;

    public MyData(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyData [id=" + id + ", value=" + value + "]";
    }
}
