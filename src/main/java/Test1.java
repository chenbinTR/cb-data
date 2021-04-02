import cn.hutool.core.util.ArrayUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.fastjson.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.SymmetricEncoder;
import utils.Utils;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

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

    public static void main(String[] args) {
        String stri="ss&*(,.~1如果@&(^-自己!!知道`什`么#是$苦%……Z，&那*()么一-=定——+告诉::;\"'/?.,><[]{}\\||别人什么是甜。";
        System.out.println(stri);

        String stri1=stri.replaceAll("\\p{Punct}","");//不能完全清除标点
        System.out.println(stri1);

        String stri2=stri.replaceAll("\\pP","");//完全清除标点
        System.out.println(stri2);

        String stri3=stri.replaceAll("\\p{P}","");//同上,一样的功能
        System.out.println(stri3);

        String stri4=stri.replaceAll("[\\pP\\p{Punct}]","");//清除所有符号,只留下字母 数字 汉字 共3类.
        System.out.println(stri4);
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
