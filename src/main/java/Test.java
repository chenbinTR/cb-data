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

/**
 * @author ChenOT
 * @date 2019-06-20
 * @see
 */
public class Test {
    /**
     * 促销活动起始时间，需要根据用户创建时间来生效
     */
    private static Date MEMBER_CREATE_DATE;
    private static Date createDate;
    private static SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String test = "111";
    private static final String API_BAIDU = "http://api.fanyi.baidu.com/api/trans/vip/translate?q=<CMD>&from=auto&to=auto&appid=%s&salt=%s&sign=%s";

    public static String convertFileToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            System.out.println("文件大小（字节）="+in.available());
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组进行Base64编码，得到Base64编码的字符串
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str = encoder.encode(data);
        return base64Str;
    }

    public static void main(String[] args) throws IOException {
        String path = "D:/203269d787c94b648f4bc3ab34b4f11f.jpg";
        System.out.println(convertFileToBase64(path));
        ImageIcon imageIcon = new ImageIcon(path);
        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();
        System.out.println(iconWidth + "," + iconHeight);


        Image image = imageIcon.getImage();
        int imageWidth = image.getWidth(imageIcon.getImageObserver());
        int imageHeight = image.getHeight(imageIcon.getImageObserver());
        System.out.println(imageWidth + "," + imageHeight);

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(path));
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            System.out.println(width + "," + height);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(ApplyClient.Others.toString());
        System.out.println(SymmetricEncoder.AESDncode("TWzsEt9wEe2UaBFVIV6gQg=="));
        System.out.println("唱一首刘德华的不能说的秘密唱一唱一首刘".getBytes("utf-8").length);

        getApiResult("hello");
    }

    private static String getApiResult(String word) {
        String result = "";

        // 百度
        try {
            String api2 = API_BAIDU.replace("<CMD>", word);
            String appid = "20170620000059608";
            String appkey = "B8VW5xjo64TUalx3U3Vr";
            int salt = (int) (Math.random() * 100);
            String md5Str = appid + word + salt + appkey;
            String sign = DigestUtils.md5Hex(md5Str);
            api2 = String.format(api2, appid, salt, sign);

            String response = Utils.httpGet(api2);
            System.out.println(response);
            net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(response);
            net.sf.json.JSONArray array = object.getJSONArray("trans_result");
            for (Object item : array) {
                net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(item);

                if (array.size() == 1) {
                    result = obj.getString("dst");
                } else {
                    result += (obj.getString("dst") + ";");
                }
            }

            if (StringUtils.isNotBlank(result)) {
                System.out.println("百度api的翻译结果：" + result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
