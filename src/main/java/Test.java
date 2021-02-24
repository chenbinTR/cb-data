import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.dictionary.py.String2PinyinConverter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import platform.ApplyClient;
import sun.misc.BASE64Encoder;
import utils.SymmetricEncoder;
import utils.Utils;
import zk.TestConstant;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-06-20
 * @see
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String path = "E:\\data\\词典\\dict-master(1)\\dict-master\\book\\";
        File root = new File(path);
        for (String s : root.list()) {
            String childSrc = path + s;
            List<String> lines = Utils.readFileToList(childSrc);
            lines.forEach(item -> {
                try {
                    JSONObject jsonObject = JSONObject.parseObject(item);

                    String word = jsonObject.getString("headWord");
                    JSONObject content = jsonObject.getJSONObject("content").getJSONObject("word").getJSONObject("content");

                    JSONArray trans = content.getJSONArray("trans");


                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(item);
                }

            });
//            for (int i = 0; i < lines.size(); i++) {
//                System.out.println(lines);
//            }
        }
//        List<String> lines = Utils.readFileToList("A\\A-b.txt");
//        lines.forEach(e-> System.out.println(e));
    }
}
