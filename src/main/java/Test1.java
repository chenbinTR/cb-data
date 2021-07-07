import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import tts.TtsRequest;
import utils.Utils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static Pattern pattern = Pattern.compile("&#.+?;");

    public static void main(String[] args) {
        System.out.println("（1）kaishi".replaceAll("（\\d+）",""));
    }

    private static void processWord(){
        List<String> lines = FileUtil.readUtf8Lines("E:\\新建文件夹\\牛津3000核心词.txt");
        String url = "http://47.94.53.111/universe-dict-v1/query";
        JSONObject param = new JSONObject();
        param.put("dicts", Arrays.asList("en_word"));
        for (String line : lines) {
            String word = StringUtils.strip(line);
            param.put("text", word);
            String result = Utils.httpPost(param.toJSONString(), url);
            int i = 1;
            try {
                if (StringUtils.isBlank(result)) {
                    i = 0;
                } else {
                    JSONObject reJson = JSONObject.parseObject(result);
                    int code = reJson.getIntValue("code");
                    if (code != 200) {
                        i = 0;
                    } else {
                        if (reJson.toJSONString().indexOf("en_word") < 0) {
                            i = 0;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                i = 0;
            }
            Utils.writeToTxt("E:\\新建文件夹\\牛津3000核心词-结果.txt", word, i + "", result.replace("\t", ""));
        }
    }

    private static void porcessChar() {
        List<String> lines = FileUtil.readUtf8Lines("E:\\新建文件夹\\中小学必须掌握的3500个常用汉字.txt");
        String url = "http://47.94.53.111/universe-dict-v1/query";
        JSONObject param = new JSONObject();
        param.put("dicts", Arrays.asList("cn_char"));
        for (String line : lines) {
            char[] items = StringUtils.strip(line).toCharArray();
            for (char item : items) {
                String word = String.valueOf(item);
                if (StringUtils.isBlank(word)) {
                    continue;
                }
                word = StringUtils.strip(word);
                param.put("text", word);
                String result = Utils.httpPost(param.toJSONString(), url);
                int i = 1;
                try {
                    if (StringUtils.isBlank(result)) {
                        i = 0;
                    } else {
                        JSONObject reJson = JSONObject.parseObject(result);
                        int code = reJson.getIntValue("code");
                        if (code != 200) {
                            i = 0;
                        } else {
                            if (reJson.toJSONString().indexOf("cn_char") < 0) {
                                i = 0;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    i = 0;
                }
                Utils.writeToTxt("E:\\新建文件夹\\中小学必须掌握的3500个常用汉字-结果.txt", word, i + "", result.replace("\t", ""));
            }
        }
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
