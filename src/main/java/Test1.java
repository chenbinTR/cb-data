import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import threadpattern.ThreadContainer;
import utils.Utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author ChenOT
 * @Description: TODO
 * @date 2021/2/25
 */
public class Test1 {
    public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void process() {
        ExcelReader excelReader = ExcelUtil.getReader(new File("E:\\汉字-音意.xlsx"));
        List<Map<String, Object>> readAll = excelReader.readAll();
        for (Map<String, Object> map : readAll) {
            try {
                String id = MapUtils.getString(map, "id", "").trim();
                String word = MapUtils.getString(map, "字", "").trim();
                String pinyin = MapUtils.getString(map, "拼音", "").trim();
                String explain = MapUtils.getString(map, "释义（|）", "").trim();
                String combinWord = MapUtils.getString(map, "组词（|）", "").trim();
                List<String> strings = new ArrayList<>();
                if (StringUtils.isNotBlank(combinWord)) {
                    String[] items = combinWord.split("\\|");
                    for (String item : items) {
                        if (StringUtils.isBlank(item) || item.length() > 4) {
                            continue;
                        }
                        strings.add(item.trim());
                    }
                }
                if (CollectionUtils.isNotEmpty(strings)) {
                    combinWord = StringUtils.join(strings.toArray(), "|");
                }
                Utils.writeToTxt("E:\\111.txt", id, word, pinyin, explain, combinWord);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        List<Integer> parseTypes = Arrays.asList(new Integer[]{28, 25, 36, 20, 104, 7, 35, 30, 2, 23, 21, 16, 4, 3, 1, 9, 8, 101,106,107,108,109, 27, 6, 5, 37, 38, 10, 11, 39, 102, 103, 10, 50});
//        String explanation = "你好 | 看见了吧| | 好";
//        List<String> explanations = Arrays.asList(explanation.split("\\|"));
//        explanations = explanations.stream().filter(e -> StringUtils.isNotBlank(e)).map(e -> StringUtils.strip(e)).collect(Collectors.toList());
//        explanations.forEach(System.out::println);

        List<String> results = new ArrayList<>();
        List<String> lines = FileUtil.readUtf8Lines("C:\\Users\\CheN\\Downloads\\log_202104.txt");
        for (String line : lines) {
            try {
                if (line.indexOf("2300101") > -1 || line.indexOf("2300102") > -1 || line.indexOf("os.sys.chat") > -1 || line.indexOf("platform.chat") > -1) {
                    String[] items = line.split("\t");
                    int parsetype = Integer.valueOf(items[1].trim());
                    String appkey = items[2].trim();
                    if (!parseTypes.contains(parsetype)) {
                        continue;
                    }
                    int channel = 0;
                    if (appkey.equals("os.sys.chat")) {
                        channel = 1;
                    }
                    results.add(parsetype + "\t" + channel);
                    if (results.size() == 100000) {
                        Utils.writeToTxt("E:\\chat.txt", StringUtils.join(results.toArray(), "\r\n"));
                        results.clear();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Utils.writeToTxt("E:\\chat.txt", StringUtils.join(results.toArray(), "\r\n"));

//            String word = line.replace("https://dict.cn/search?q=", "");
//            if(StringUtils.isBlank(word)){
//                continue;
//            }
//            word = StringUtils.strip(word);
//            word = word.replace("\t", "");
//            if(word.indexOf(" ")<0){
//                continue;
//            }
//            word = replaceStartEndPunctuation(word);
//            if(isContainCn(word)){
//                System.err.println(word);
//                continue;
//            }
//            Utils.writeToTxt("E:\\2.txt", StringUtils.strip(word), "xw", "20");
//        }
    }


    public static boolean isContainCn(String str) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9'\\s\\.\\?-]");
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 以非汉字、非英文字母开始的所有字符
     */
    private static final Pattern START_PUNCTUATION = Pattern.compile("^[^a-zA-Z0-9\u4e00-\u9fa5]+");
    /**
     * 以非汉字、非英文字母开始的所有字符
     */
    private static final Pattern END_PUNCTUATION = Pattern.compile("[^a-zA-Z0-9\u4e00-\u9fa5]+$");

    /**
     * 去掉首尾所有非字母汉字
     *
     * @param str
     * @return
     */
    public static String replaceStartEndPunctuation(String str) {
        str = START_PUNCTUATION.matcher(str).replaceAll("");
        str = END_PUNCTUATION.matcher(str).replaceAll("");
        return str;
    }


}
