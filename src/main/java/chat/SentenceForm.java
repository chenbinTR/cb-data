package chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-02-13
 * @see
 * @since
 */
public class SentenceForm {
    private static final String path = "C:\\Users\\cb\\Downloads\\";
    private static String url = "http://192.168.10.195:19419/nlp/sentence-type?query=%s";

    public static void readFileToList(String filePath) throws IOException {
        int count = 0;
        List<JSONObject> jsonObjects = new ArrayList<>();
        List<String> list = new ArrayList<String>();
        File file = new File(filePath);
        BufferedReader reader = null;
        if (file.exists()) {

            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF-8"));
            String temp = null;
            String type = null;
            while (null != (temp = reader.readLine())) {
                try {
                    System.out.println(++count);
                    JSONObject json = JSON.parseObject(temp.trim());
                    JSONArray infos = json.getJSONArray("infos");
                    String answer = infos.getJSONObject(0).getString("content");
                    type = getSentenceType(answer);
                    if (type == null) {
                        Utils.writeToTxt(path + "kv_data_sentence_exception.txt", temp);
                        continue;
                    }
                    String[] tpyes = type.split("\t");
                    JSONObject tags = json.getJSONObject("tags");
                    if (tags == null) {
                        tags = new JSONObject();
                    }
                    tags.put("sentence_form", tpyes[0]);
                    tags.put("sentence_form_score", tpyes[1]);
                    json.put("tags", tags);
//                    jsonObjects.add(json);
//                    if (jsonObjects.size() == 10000) {
                    Utils.writeToTxt(path + "kv_data_sentence_form.txt", json.toJSONString());
//                        jsonObjects.clear();
//                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    Utils.writeToTxt(path + "kv_data_sentence_exception.txt", temp);
                }
            }
//            Utils.writeToTxt(path + "chat_answer_sentence_new_model.txt", StringUtils.join(jsonObjects.toArray(), "\r\n"));
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    public static String getSentenceType(String str) {
        try {
            String result = Utils.httpGet(String.format(url, URLEncoder.encode(str, "UTF-8")));
            JSONObject jsonObject = JSON.parseObject(result);
            return jsonObject.getJSONObject("data").getString("senttype") + "\t" + jsonObject.getJSONObject("data").getString("score");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        readFileToList(path + "kv_data_20190517.txt");
    }
}
