import java.util.Scanner;
/**
* @author fourous
* @date: 2020/3/7
* @description: 字符串最后一个单词的长度
*/

public class WordLen {
// 调用系统函数分割成数组
//	public static void main(String[] args){
//		Scanner scanner = new Scanner(System.in);
//		String string = scanner.nextLine();
//		String[] strings = string.split(" ");
//		int len = strings[strings.length - 1].length();
//		System.out.println(len);
//	}

// 反向扫描，不调用系统函数
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		String string = scanner.nextLine();
		int len = string.length();
		int count = 0;
		for(int i = len - 1; i >= 0; i--){
			if(string.charAt(i) == ' '){
				break;
			}
			count++;
		}
		System.out.println(count);
	}
}
