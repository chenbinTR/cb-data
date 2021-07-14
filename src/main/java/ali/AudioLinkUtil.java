package ali;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * oss url音频加上过期时间
 *
 * @User: fengxinxin
 * @Date: 2019/3/21
 * @Time: 17:32
 * @copyright uzoo
 */
public class AudioLinkUtil {

    /**
     * cdn 鉴权url加上过期时间
     *
     * @param url
     * @return
     */
    public static String parseAudioUrl(String url) {
        return cdn(url);
    }

    //全站加速
    public static String slbUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        url = url.replaceAll("universe-file.tuling123.com", "iot-file.turingos.cn");
        url = url.replaceAll("https", "http");
        return url;
    }

    /**
     * cdn url加上过期时间
     *
     * @param url
     * @return
     */
    public static String cdn(String url) {
        if (StringUtils.isBlank(url)) {
            return "";
        }
        if (url.indexOf("universe-file.tuling123.com") > -1 || url.indexOf("turing-universe.oss-cn-beijing.aliyuncs.com") > -1) {
            String domain = "http://universe-file-limit.tuling123.com";
            url = url.replaceAll("universe-file", "universe-file-limit");
            if (url.indexOf("turing-universe.oss-cn-beijing.aliyuncs.com") > -1)
                url = url.replaceAll("turing-universe.oss-cn-beijing.aliyuncs.com", "universe-file-limit.tuling123.com");
            url = url.replaceAll("https", "http");
            String path = url.substring(domain.length());
            String date = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmm");
            String key = "2acbb6bebed51f8843f560e885a66d4c";
            String hash = md5(key + date + path);
            return domain + "/" + date + "/" + hash + path;
        }
        return url;
    }

    public static String md5(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] datas = md.digest(content.getBytes());
            BigInteger number = new BigInteger(1, datas);
            String hash = number.toString(16);
            while (hash.length() < 32) {
                hash = "0" + hash;
            }
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String s = parseAudioUrl("https://universe-file.tuling123.com/dict_image/image_1380.jpg");
        System.out.println(s);
    }


}
