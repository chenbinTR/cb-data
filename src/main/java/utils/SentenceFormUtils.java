package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import vo.SentenceFormVO;

import java.net.URLEncoder;

/**
 * @author ChenOT
 * @date 2019-05-17
 * @see
 * @since
 */
public class SentenceFormUtils {
    private static final String URL = "http://192.168.10.195:19419/nlp/sentence-type?query=%s";

    /**
     * 获取句子的句型和分数
     *
     * @param str
     * @return
     */
    public static SentenceFormVO getSentenceForm(String str) {
        try {
            String result = Utils.httpGet(String.format(URL, URLEncoder.encode(str, "UTF-8")));
            JSONObject jsonObject = JSON.parseObject(result);
            return new SentenceFormVO(jsonObject.getJSONObject("data").getString("senttype"), jsonObject.getJSONObject("data").getString("score"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
