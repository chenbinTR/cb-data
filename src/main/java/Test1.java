import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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
        ExcelReader excelReader = ExcelUtil.getReader(new File("E:\\DICT_DATA\\入库\\成语\\成语词库.xlsx"));
        List<Map<String, Object>> readAll = excelReader.readAll();
        for (Map<String, Object> map : readAll) {
            try {
                String word = MapUtils.getString(map, "word", "").trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String replaceStartEndPunctuation(String str) {
        str = Pattern.compile("^[^a-zA-Z\u4e00-\u9fa5]+").matcher(str).replaceAll("");
        str = Pattern.compile("[^a-zA-Z\u4e00-\u9fa5]+$").matcher(str).replaceAll("");
//        str = Pattern.compile("$\\pP+").matcher(str).replaceAll("");
        return str;
    }

    public static void main(String[] args) {
        String str = "，,'。     a      你'好    。";
        System.out.println(replaceStartEndPunctuation(str));
//        process();
//        System.out.println("你好adbc c/F".replaceAll("[a-zA-z]","").trim());
//        List<String> lines = Utils.readFileToList("E:\\1.txt");

//        Set<String> lineSet = lines.stream().collect(Collectors.toSet());
//        System.out.println(lines.size());
//        System.out.println(lineSet.size());

//        List<String> files = Utils.getPathFileName("E:\\小字慢");
//        files.forEach(System.out::println);
//        Consumer<String> stringConsumer = e -> {
//            e =
//        };
//        files.forEach(stringConsumer);
//        files.forEach(e -> {
//            e = e.replace("E:\\小字慢\\", "");
//        });
//        files.forEach(System.out::println);
//        files.forEach(stringConsumer);
//        List<String> filesNew = new ArrayList<>();
//        files.forEach(e -> filesNew.add(e.replace("E:\\小字慢\\", "")));
//        lines.forEach(e->{
//            if(!filesNew.contains(e)){
//                System.out.println(e);
//            }
//        });
    }

    public static void toPinyin(String str) {
    }

    public static List<String> splitString(String str, String... separators) {
        List<String> contents = new ArrayList<>();
        contents.add(str);


        for (String seprator : separators) {
            List<String> tempList = new ArrayList<>();
            for (String content : contents) {
                tempList.addAll(Arrays.asList(content.split(seprator)));
            }
            contents.clear();
            contents.addAll(tempList);
        }
        return contents;
    }

}
