package chat;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import utils.UtilsMini;

import java.util.List;

public class ImprotEs {
    private static String path = "/home/developer/bat/";
    private static String[] urls = {"http://10.67.2.171:9201/nlp_faq_cs_21-03-22/doc?routing=", "http://10.31.184.43:9201/nlp_faq_cs_21-03-22/doc?routing=", "http://10.31.144.222:9201/nlp_faq_cs_21-03-22/doc?routing="};

    public static void main(String[] args) {
        method2();
        method1();
    }

    private static void method1() {
        List<String> datas = UtilsMini.readFileToList(path + "faq_simi.txt");
        for (String data : datas) {
            try {
                int index = RandomUtils.nextInt(0, 3);
                JSONObject jsonObject = JSONObject.parseObject(data);
                String account = jsonObject.getString("account");
                UtilsMini.httpPost(data, urls[index] + account);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(data);
                UtilsMini.writeToTxt(path + "faq_simi_error.txt", data);
            }
        }
    }

    private static void method2() {
        List<String> datas = UtilsMini.readFileToList(path + "faq.txt");
        for (String data : datas) {
            try {
                int index = RandomUtils.nextInt(0, 3);
                JSONObject jsonObject = JSONObject.parseObject(data);
                String account = jsonObject.getString("account");
                UtilsMini.httpPost(data, urls[index] + account);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(data);
                UtilsMini.writeToTxt(path + "faq_error.txt", data);
            }
        }
    }
}
