import java.util.Scanner;

/**
* @author fourous
* @date: 2020/3/9
* @description:写出一个程序，接受一个正浮点数值，输出该数值的近似整数值。如果小数点后数值大于等于5,向上取整；小于5，则向下取整。
 * 强转是直接取整了
 * 注意这里使用三元表达式，一般这种选择题目都可以用这个
*/
public class oj_7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            float number = scanner.nextFloat();
            System.out.println(toInt(number));
        }
    }
    public static int toInt(float number){
        int i = (int) number;
        return number-i>=0.5?i+1:i;
    }
}
