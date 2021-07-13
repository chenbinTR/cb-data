import cn.hutool.core.convert.NumberChineseFormatter;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
//        System.out.println(NumberChineseFormatter.chineseToNumber("五六"));
        System.out.println(chineseNumber2Int("开始"));
//        final String P_NAMED = ".*(适合|适用).*?(?<age>[\\d一二三四五六七八九十]+)岁.*";
//        final String DATE_STRING = "适合五六岁的宝宝吗";
//
//        Pattern pattern = Pattern.compile(P_NAMED);
//        Matcher matcher = pattern.matcher(DATE_STRING);
//        if(!matcher.matches()){
//            return;
//        }
//        matcher.find();
//        System.out.printf("\n===========使用名称获取=============");
//        System.out.printf("\nmatcher.group(0) value:%s", matcher.group(0));
//        System.out.printf("\n matcher.group('age') value:%s", matcher.group("age"));
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

    /**
     * 中文數字转阿拉伯数组【十万九千零六十  --> 109060】
     * @author 雪见烟寒
     * @param chineseNumber
     * @return
     */
    private static int chineseNumber2Int(String chineseNumber){
        int result = 0;
        int temp = 1;//存放一个单位的数字如：十万
        int count = 0;//判断是否有chArr
        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
        char[] chArr = new char[]{'十','百','千','万','亿'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if(b){//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                            case 0:
                                temp *= 10;
                                break;
                            case 1:
                                temp *= 100;
                                break;
                            case 2:
                                temp *= 1000;
                                break;
                            case 3:
                                temp *= 10000;
                                break;
                            case 4:
                                temp *= 100000000;
                                break;
                            default:
                                break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }
        return result;
    }
}
