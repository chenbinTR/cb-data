package turingshield;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.Utils;

/**
 * @author ChenOT
 * @date 2019-08-16
 * @see
 * @since
 */
public class TuringShieldUtil {
    /**
     * 请求图灵盾
     * @param url
     * @param text
     * @return
     */
    public static String requestTuringShield(String url, String text){
        JSONObject item;
        JSONArray ja = new JSONArray();
        try {
            item = new JSONObject();
            item.put("question", text);
            item.put("userID", "0");
            item.put("reqID", "1");
            ja.add(item);
            return Utils.httpPost(ja.toJSONString(), url);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error-url: "+url+" text: "+text);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(requestTuringShield("http://101.201.68.245:8087/v3/turing-shield", "item:{\"text\":\"您想问哪方面的问题呢？\",\"value\":[\"北京社保公积金缴纳比例\",\"上海社保公积金缴纳比例\",\"广州社保公积金缴纳比例\",\"北京公积金提取\",\"上海公积金提取\",\"上海社保卡更新\"],\"askHr\":\"Y\"}"));
    }
}
