package baidu;

import com.alibaba.fastjson.JSONObject;
import utils.Utils;

/**
 * @author ChenOT
 * @date 2019-06-12
 * @see
 * @since
 */
public class AccessTokenUtil {
    public static final String API_KEY = "s9kuDsbDYnbg2uNdRlbburYl";
    public static final String SECRET_KEY = "kCK3bEVG3kUUrl0OnRViPBGVZr4FetCf";

    public static String URL = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s";

    public static void main(String[] args) {
        JSONObject jsonObject = JSONObject.parseObject(Utils.httpGet(String.format(URL, API_KEY, SECRET_KEY)));
        System.out.println(jsonObject.getString("access_token"));
    }
}
