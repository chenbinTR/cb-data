import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.dictionary.py.String2PinyinConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import utils.SymmetricEncoder;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-06-20
 * @see
 * @since
 */
public class Test {
    /**
     * 促销活动起始时间，需要根据用户创建时间来生效
     */
    private static Date MEMBER_CREATE_DATE;
    private static Date createDate;
    private static SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String test = "111";
    public static void main(String[] args) throws IOException {
        System.out.println();

        System.out.println(SymmetricEncoder.AESEncode("YIEK3ijSwfxYrINxvmPBLHXIEHlf7m"));
        System.out.println(test.replace("1", "df"));
//        Float f1 = 0.5672F;
//        Float f2 = 0.5671F;
//
//        for (int i=0;i<10;i++){
//            System.out.println(f1.compareTo(f2));
//        }
//
//        int value = -10;
//        int a = 100;
//        System.out.println(a+value);
//
//        LocalDate date = LocalDate.now();
//        System.out.println(date);
//        System.out.println(Integer.valueOf("001"));
//        ThreadLocalRandom.current().nextInt(0);
        JSONArray ja = new JSONArray();
        ja.add("1");
        ja.add("2");

        System.out.println(ja.toArray(new String[0]));
    }
}
