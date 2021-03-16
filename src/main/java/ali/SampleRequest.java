package ali;

import com.alibaba.fastjson.JSONObject;
import utils.Utils;

import java.util.List;

public class SampleRequest {
    private static String serviceURL = "http://mt.cn-hangzhou.aliyuncs.com/api/translate/web/general";
    private static String accessKeyId = "";// 使用您的阿里云访问密钥 AccessKeyId
    private static String accessKeySecret = ""; // 使用您的阿里云访问密钥

    public static void main(String[] args) {
        batchProcess();
//        List<String> lines = Utils.readFileToList("E:\\trans\\2_ali_result.txt");
//        for (String line : lines) {
//            String[] items = line.split("\t");
//            if (items.length > 1) {
//
//            } else {
//                System.out.println(items[0] + "\t" + getAliTransRe(items[0]));
//            }
//        }
    }

    private static void batchProcess() {
        List<String> lines = Utils.readFileToList("E:\\trans\\4.txt");
        long time1 = System.currentTimeMillis();
        for (String line : lines) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            getAliTransRe(line);
//            Utils.writeToTxt("E:\\trans\\3_ali_result.txt", line, getAliTransRe(line));
        }
        System.out.println(System.currentTimeMillis() - time1);
    }

    private static String getAliTransRe(String text) {
        JSONObject postBody = new JSONObject();
        postBody.put("FormatType", "text");
        postBody.put("SourceLanguage", "en");
        postBody.put("TargetLanguage", "zh");
        postBody.put("SourceText", text);
        postBody.put("Scene", "general");
        String dst = "";
        String result = "";
        try {
            result = Sender.sendPost(serviceURL, postBody.toJSONString(), accessKeyId, accessKeySecret);
            dst = JSONObject.parseObject(result).getJSONObject("Data").getString("Translated");
        } catch (Exception e) {
            System.err.println(text + "\t" + result);
        }
        return dst;
    }
}
