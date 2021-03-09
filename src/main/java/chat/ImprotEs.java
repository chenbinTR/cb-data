package chat;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import utils.UtilsMini;

import java.util.List;

public class ImprotEs {
    private static String path = "/home/developer/bat/";
    //    private static String path = "E:\\";
//    private static String[] urls = {"http://10.67.2.171:9201/nlp_faq_cs/doc", "http://10.31.184.43:9201/nlp_faq_cs/doc", "http://10.31.144.222:9201/nlp_faq_cs/doc"};
    private static String[] urls = {"http://10.45.140.56:9201/nlp_chat/doc", "http://10.45.136.161:9201/nlp_chat/doc"};

    public static void main(String[] args) {
        method2();
    }

    private static void method2() {
        List<String> datas = UtilsMini.readFileToList(path + "chat_q.txt");
        for (String data : datas) {
            try {
                int index = RandomUtils.nextInt(0, 2);
                JSONObject.parseObject(data);
                UtilsMini.httpPost(data, urls[index]);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(data);
                UtilsMini.writeToTxt(path + "chat_q_error.txt", data);
            }
        }
    }

    private void method1() {
        List<String> datas = UtilsMini.readFileToList(path + "q.txt");
        for (String data : datas) {
            try {
                JSONObject jsonObject = new JSONObject();
                String question = data.split("\t")[1];
                jsonObject.put("id", Integer.valueOf(data.split("\t")[0]));
                jsonObject.put("question", question);
                jsonObject.put("table_name", "chat_question");
                String preprocess = PreProcessService.process(question);
                if (StringUtils.isNotBlank(preprocess) && !question.equals(preprocess)) {
                    jsonObject.put("question_preprocessed", preprocess);
                }
                UtilsMini.writeToTxt(path + "chat_q.txt", jsonObject.toJSONString());

//                UtilsMini.httpPost(jsonObject.toJSONString(), "http://10.67.2.171:9201/nlp_chat/doc");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(data);
                UtilsMini.writeToTxt(path + "chat_error.txt", data);
            }


        }
    }
}
