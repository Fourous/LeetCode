import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/9
 * @description: 输出质数因子
 */
public class oj_6 {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            long number = scan.nextLong();
            System.out.println(getResult(number));
        }
    }
    public static String getResult(long ulDataInput){
        String string = "";
        while(ulDataInput!=1){
            for(int i=2;i<=ulDataInput;i++){
                if(ulDataInput%i==0){
                    string+=i;
                    ulDataInput/=i;
                    string+=" ";
                    break;
                }
            }
        }
        return string;
    }
}
