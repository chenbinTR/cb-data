import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import utils.Utils;

import java.io.File;
import java.util.List;

/**
 * @author ChenOT
 * @Description: TODO
 * @date 2021/2/25
 */
public class Test1 {
    private static String[] urls = {"http://10.67.2.171:9201/nlp_faq_cs/doc", "http://10.31.184.43:9201/nlp_faq_cs/doc", "http://10.31.144.222:9201/nlp_faq_cs/doc"};
    private static void tttt() {
        int index = RandomUtils.nextInt(0, 3);
        System.out.println(index);
        System.out.println(urls[index]);

    }

    public static void main(String[] args) {
        File folder = new File("E:\\扫读笔测试集");
        String[] files = folder.list();
        System.out.println(files.length);

        List<String> lines = Utils.readFileToList("E:\\1.txt");
        System.out.println(lines.size());
        String allLine = StringUtils.join(files, "|");
        for (String file : lines) {
            if(allLine.indexOf(file)<0){
                System.out.println(file);
            }
        }
//        tttt();
//        File file = new File("E:\\en.z");
//        List<String> lines = Utils.readFileToList("E:\\en.z", "gbk");
//        lines.forEach(line-> System.out.println(line));
//        System.out.println(SymmetricEncoder.AESEncode("47.95.29.224"));

//        List<String> datas = Utils.readFileToList("E:\\q.txt");
//        for (String data : datas) {
//            try {
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("id", Integer.valueOf(data.split("\t")[0]));
//                jsonObject.put("question", data.split("\t")[1]);
//                jsonObject.put("table_name", "chat_question");
////                Utils.writeToTxt("E:\\chat.json", "{ \"index\":{} }");
////                Utils.writeToTxt("E:\\chat.text", jsonObject.toJSONString());
//                Utils.httpPost(jsonObject.toJSONString(), "http://47.95.29.224:9201/nlp_chat/doc");
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.err.println(data);
//                Utils.writeToTxt("E:\\chat_error.txt", data);
//            }
//
//
//        }

//        List<String> contents = Utils.readFileToList("E:\\4.txt");
//        JSONArray records = new JSONArray();
//        int count = 0;
//        for (String content : contents) {
//            ++count;
//            JSONObject item = new JSONObject();
//            item.put("id", count);
//            item.put("uQuestion",content);
//            records.add(item);
//        }
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("type","tuling");
//        jsonObject.put("data","tuling");
//        jsonObject.put("total",1000);
//        jsonObject.put("records",records);
//
//        Utils.httpPost(jsonObject.toJSONString(), "http://localhost:23335/huitian/cluster");

    }
}
