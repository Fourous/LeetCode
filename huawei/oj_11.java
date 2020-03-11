import java.util.Scanner;

public class oj_11 {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            String s= scan.nextLine();
            char[] chars = s.toCharArray();
            String result="";
            for(int i=chars.length-1;i>=0;i--){
                result+=chars[i];
            }
            System.out.println(result);
        }
    }
}
