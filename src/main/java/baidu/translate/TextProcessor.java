package baidu.translate;

import com.alibaba.fastjson.JSONObject;
import utils.Utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ChenOT
 * @Description: TODO
 * @date 2021/2/5
 */
public class TextProcessor {
    private static Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
    private static final String appid = "20191118000358247";
    private static final String key = "99JEIjXikaExjT5VJ5hA";
    private static final TransApi transApi = new TransApi(appid, key);

    public static void main(String[] args) {
        batchProcess();
    }

    private static void batchProcess(){
        List<String> lines = Utils.readFileToList("E:\\trans\\6.txt");
        long time1 = System.currentTimeMillis();
        for (String line : lines) {
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            getOneRe(line);
//            Utils.writeToTxt("E:\\trans\\3_result.txt", line, getOneRe(line));
        }
        System.out.println(System.currentTimeMillis() - time1);
    }

    private static String getOneRe(String text) {
        String result = "";
        String dst = "";
        try {
            result = transApi.getTransResult(text, "en", "zh");
            dst = JSONObject.parseObject(result).getJSONArray("trans_result").getJSONObject(0).getString("dst");
//            dst = unicodeDecode(dst);
        } catch (Exception e) {
            System.err.println(text + "\t" + result);
        }
        return dst;
    }

    private static String unicodeDecode(String string) {
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }
}
