import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author fourous
 * @date: 2020/3/10
 * @description: 1、 记录最多8条错误记录，循环记录（或者说最后只输出最后出现的八条错误记录），对相同的错误记录（净文件名称和行号完全匹配）只记录一条，错误计数增加；
 * 2、 超过16个字符的文件名称，只记录文件的最后有效16个字符；
 * 3、 输入的文件可能带路径，记录文件名称不能带路径。
 * <p>
 * 由于LinkedHashMap能保持输入数据顺序的，所以这里用LinkHashMap 不要直接用HashMap
 */
public class oj_14 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // 由于输出有先后顺序，需要用到LinkHashMap
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        while (scan.hasNext()) {
            String fileUrl = scan.next();
            int num = scan.nextInt();
            String[] fileUrls = fileUrl.split("\\\\");
            String originName = fileUrls[fileUrls.length - 1];
            if (originName.length() > 16) {
                originName = originName.substring(originName.length() - 16);
            }
            // 不仅需要裁剪文件名，而且注意是以文件名和错误数一起做Key值
            originName = originName + " " + num;
            map.merge(originName, 1, (oldVal, newVal) -> oldVal + 1);
        }
        // 输出最后8个
        int count = 0;
        for (String key : map.keySet()) {
            count++;
            if (count > (map.keySet().size() - 8)) {
                System.out.println(key + " " + map.get(key));
            }
        }
    }
}
