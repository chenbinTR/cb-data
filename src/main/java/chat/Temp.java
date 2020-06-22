package chat;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author wangsx
 * @Title: chat.Temp
 * @Description: TODO
 * @date 2020/1/21
 */
public class Temp {
    private static List<String> keys;
    private static Map<Integer, Set<String>> answersMap = new HashMap<>();
    private static String path = "Q:\\logs\\";
    private static Map<Integer, String> questions = new HashMap<>();

    private static void loadQandA() throws IOException {
        LineIterator lineIterator = FileUtils.lineIterator(new File(path + "sensitive_kv_data.txt"));
        while (lineIterator.hasNext()) {
            try {
                String line = lineIterator.nextLine();
                JSONObject jsonObject = JSONObject.parseObject(line);
                Integer qid = jsonObject.getInteger("qid");
                String question = jsonObject.getString("question");
                String type = jsonObject.getString("type");
                String id = jsonObject.getJSONObject("_id").getString("$oid");
                String content = jsonObject.getJSONArray("infos").getJSONObject(0).getString("content");
                String sensitiveWord = jsonObject.getString("sensitive_word");
                Utils.writeToTxt(path + "sensitive_sensitive_kv_data_2.txt", id + "\t" + qid + "\t" + type + "\t" + question + "\t" + content + "\t" + sensitiveWord);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void readQuestion() throws IOException {
        LineIterator lineIterator = FileUtils.lineIterator(new File(path + "dialogue_2020-05-25.txt"));
        while (lineIterator.hasNext()) {
            try {
                String line = lineIterator.nextLine();
                JSONObject jsonObject = JSONObject.parseObject(line);
                Integer qid = jsonObject.getInteger("qid");
                String question = jsonObject.getString("question");
                questions.put(qid, question);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void getQuestion() throws IOException {
        LineIterator lineIterator = FileUtils.lineIterator(new File(path + "sensitive_answer.txt"));
        while (lineIterator.hasNext()) {
            try {
                String line = lineIterator.nextLine();
                JSONObject jsonObject = JSONObject.parseObject(line);
                String type = jsonObject.getString("type");
                if (!type.equals("dialogue")) {
                    continue;
                }
                Integer qid = jsonObject.getInteger("qid");
                jsonObject.put("question", questions.get(qid));
                Utils.writeToTxt(path + "sensitive_answer_1.txt", jsonObject.toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void filterProcess() throws IOException {
        Set<String> keys = Utils.readFileToSet(path + "key.txt");
        List<String> ids = new ArrayList<>();
        LineIterator lineIterator = FileUtils.lineIterator(new File(path + "kv_data_2020-05-25.txt"));
        int count = 0;
        while (lineIterator.hasNext()) {
            try {
                String line = lineIterator.nextLine();
                JSONObject jsonObject = JSONObject.parseObject(line);
                String id = jsonObject.getJSONObject("_id").getString("$oid");
                if (ids.contains(id)) {
                    continue;
                }
                ids.add(id);
                String content = jsonObject.getJSONArray("infos").getJSONObject(0).getString("content");

                StringBuilder stringBuilder = new StringBuilder();
                if (StringUtils.isNotBlank(content)) {
                    for (String key : keys) {
                        if (StringUtils.isBlank(key)) {
                            continue;
                        }
                        if (content.indexOf(key) > -1) {
                            stringBuilder.append(key);
                            stringBuilder.append("|");
                        }
                    }
                }
                if (stringBuilder.length() > 0) {
                    jsonObject.put("sensitive_word", StringUtils.strip(stringBuilder.toString(), "|"));
                    Utils.writeToTxt(path + "sensitive_kv_data.txt", jsonObject.toJSONString());
                }
                System.out.println(++count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void filterProcess1() throws IOException {
        Set<String> keys = Utils.readFileToSet(path + "key.txt");
        List<String> ids = new ArrayList<>();
        LineIterator lineIterator = FileUtils.lineIterator(new File(path + "common_chat_answer_2020-05-25.txt"));
        int count = 0;
        while (lineIterator.hasNext()) {
            try {
                String line = lineIterator.nextLine();
                StringBuilder stringBuilder = new StringBuilder();
                if (StringUtils.isNotBlank(line)) {
                    for (String key : keys) {
                        if (StringUtils.isBlank(key)) {
                            continue;
                        }
                        if (line.indexOf(key) > -1) {
                            stringBuilder.append(key);
                            stringBuilder.append("|");
                        }
                    }
                }
                if (stringBuilder.length() > 0) {
                    Utils.writeToTxt(path + "sensitive_commont_chat_answer.txt", line + "\t" + StringUtils.strip(stringBuilder.toString(), "|"));
                }
                System.out.println(++count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        filterProcess();
//        readQuestion();
//        getQuestion();
//        loadQandA();
//        filterProcess1();
    }
}
