import java.util.*;

public class oj_19 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            int num = Integer.valueOf(scan.nextLine());
            String[] input = new String[num];
            for (int i = 0; i < num; i++) {
                String str = scan.nextLine();
                input[i] = str;
            }
            for (int j = 0; j < input.length; j++) {
                countName(input[j]);
            }
        }
    }

    public static void countName(String str) {
        char[] chars = str.toLowerCase().toCharArray();
        HashMap<Character, Integer> hashMap = new HashMap<Character, Integer>();
        for (int i = 0; i < chars.length; i++) {
            hashMap.merge(chars[i], 1, (oldVal, newVal) -> oldVal + 1);
        }
        int[] numArray = new int[hashMap.size()];
        int count = 0;
        for (int value : hashMap.values()) {
            numArray[count++] = value;
        }
        Arrays.sort(numArray);
        int all = 0;
        int alpha = 26;
        for (int j = numArray.length - 1; j >= 0; j--) {
            all += numArray[j] * alpha;
            alpha--;
        }
        System.out.println(all);
    }
}
