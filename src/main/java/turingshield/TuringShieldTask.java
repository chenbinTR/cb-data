package turingshield;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.Utils;

import java.util.concurrent.Callable;

/**
 * @author ChenOT
 * @date 2019-08-08
 * @see
 * @since
 */
public class TuringShieldTask implements Callable<String> {
    private final String que;
    private final String url;
    private final String resultFile;

    public TuringShieldTask(String que, String url, String resultFile) {
        this.que = que;
        this.url = url;
        this.resultFile = resultFile;
    }

    @Override
    public String call() throws Exception {
        JSONArray ja = new JSONArray();
        JSONObject item;
        try {
            item = new JSONObject();
            item.put("question", que);
            item.put("userID", "0");
            item.put("reqID", "1");
            ja.add(item);

            String result = Utils.httpPost(ja.toJSONString(), url);
            JSONObject jsonObject = JSONObject.parseObject(result).getJSONArray("datas").getJSONObject(0);
            int violenceInfo = jsonObject.getJSONObject("violenceInfo").getInteger("level");
            int pornoInfo = jsonObject.getJSONObject("pornoInfo").getInteger("level");
            int sensitiveInfo = jsonObject.getJSONObject("sensitiveInfo").getInteger("level");
//            return String.format("%s\t%d\t%d\t%d", que, violenceInfo, pornoInfo, sensitiveInfo);
            Utils.writeToTxt(resultFile, String.format("%s\t%d\t%d\t%d", que, violenceInfo, pornoInfo, sensitiveInfo));
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(que);
            Utils.writeToTxt(resultFile, String.format("%s\t%d\t%d\t%d", que, 0, 0, 0));
//            return String.format("%s\t%d\t%d\t%d", que, 0, 0, 0);
            return null;
        }
    }
}
