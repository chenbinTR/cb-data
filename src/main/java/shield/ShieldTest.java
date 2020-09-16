package shield;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import utils.ExcelEntity;
import utils.Utils;
import utils.UtilsMini;

import java.util.Arrays;
import java.util.List;

/**
 * @author ChenOT
 * @date 2020-08-26
 * @see
 * @since
 */
public class ShieldTest {
    private static String url = "http://47.94.59.188:8087/v3/turing-shield";

    public static void main(String[] args) {
        List<String> contents = UtilsMini.readFileToList("E:\\logs\\key.txt");
        for (String content : contents) {
            UtilsMini.writeToTxt("E:\\logs\\key_re.txt", content+"\t"+getShielcLevel(content));
        }
//        isSensitive("毛泽东是傻逼");
//        List<ExcelEntity> excelEntityList = Utils.readXml("E:\\logs\\中国，世界之最（1千语料）_JZQ_20200702.xlsx", 1);
//        int count = 0;
//        for (ExcelEntity excelEntity : excelEntityList) {
//            System.out.println(++count);
//            int flag = 0;
//            if (isSensitive(excelEntity.getValue0())) {
//                flag = 1;
//            }
//            if (flag == 0 && isSensitive(excelEntity.getValue1())) {
//                flag = 1;
//            }
//            if (flag == 0 && isSensitive(excelEntity.getValue2())) {
//                flag = 1;
//            }
//            if (flag == 0 && isSensitive(excelEntity.getValue3())) {
//                flag = 1;
//            }
//            if (flag == 0 && isSensitive(excelEntity.getValue4())) {
//                flag = 1;
//            }
//            Utils.writeToTxt("E:\\logs\\中国之最.txt", flag + "");
//        }
    }

    private static boolean isSensitive(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        try {
            JSONArray params = new JSONArray(1);
            JSONObject object = new JSONObject();
            object.put("question", text);
            params.add(object);

            String result = Utils.httpPost(params.toJSONString(), url);

            JSONObject resultJson = JSONObject.parseObject(result);
            JSONArray datas = resultJson.getJSONArray("datas");
            JSONObject item = datas.getJSONObject(0);
            int sensitiveLevel = item.getJSONObject("sensitiveInfo").getInteger("level");
            int violenceLevel = item.getJSONObject("violenceInfo").getInteger("level");
            int pornoLevel = item.getJSONObject("pornoInfo").getInteger("level");

            if (sensitiveLevel > 10 || violenceLevel > 10 || pornoLevel > 10) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static String getShielcLevel(String text) {
        if (StringUtils.isBlank(text)) {
            return "10\t10\t10";
        }
        try {
            JSONArray params = new JSONArray(1);
            JSONObject object = new JSONObject();
            object.put("question", text);
            params.add(object);

            String result = Utils.httpPost(params.toJSONString(), url);

            JSONObject resultJson = JSONObject.parseObject(result);
            JSONArray datas = resultJson.getJSONArray("datas");
            JSONObject item = datas.getJSONObject(0);
            int sensitiveLevel = item.getJSONObject("sensitiveInfo").getInteger("level");
            int violenceLevel = item.getJSONObject("violenceInfo").getInteger("level");
            int pornoLevel = item.getJSONObject("pornoInfo").getInteger("level");

            return sensitiveLevel + "\t" + violenceLevel + "\t" + pornoLevel;
        } catch (Exception e) {
            e.printStackTrace();
            return "10\t10\t10";
        }
    }
}
