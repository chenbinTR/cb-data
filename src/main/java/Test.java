import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.dictionary.py.String2PinyinConverter;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import platform.ApplyClient;
import utils.SymmetricEncoder;
import utils.Utils;
import zk.TestConstant;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
        System.out.println(TestConstant.i);

        Map<String, String> map = new HashMap<>(10);
        map.put("1","10");
        map.put("2","20");
        map.put("3","30");
        map.put("4","40");

        System.out.println(map.toString());

        System.out.println(map.keySet().removeIf(e -> map.get(e).equals("0")));

        System.out.println(map.toString());
        System.out.println();
//        System.out.println(ApplyClient.Others.toString());
        System.out.println(SymmetricEncoder.AESDncode("TWzsEt9wEe2UaBFVIV6gQg=="));
        System.out.println("唱一首刘德华的不能说的秘密唱一唱一首刘".getBytes("utf-8").length);
    }
}
