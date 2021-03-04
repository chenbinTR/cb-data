package faq;

import chat.PreProcessService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import java.util.List;

public class FaqPreprocess {
    public static void main(String[] args) {
        List<String> lines = Utils.readFileToList("E:\\faq_simi.txt");
        for (String line : lines) {
            JSONObject item = JSONObject.parseObject(line);
//            String keywords = item.getString("keywords");
//            if (StringUtils.isNotBlank(keywords)) {
//                if (keywords.equals("[]") || keywords.indexOf("多个关键词") > -1) {
//                    keywords = null;
//                } else {
//                    JSONArray words = JSONArray.parseArray(keywords);
//                    keywords = StringUtils.join(words.toArray(), ",");
//                    System.out.println(keywords);
//                }
//                item.put("keywords", keywords);
//            }
            String question = item.getString("question");
            try {
                String preprocess = PreProcessService.process(question);
                if (StringUtils.isNotBlank(preprocess) && !question.equals(preprocess)) {
                    item.put("question_preprocessed", preprocess);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Utils.writeToTxt("E:\\faq_simi_new.txt", item.toJSONString());
        }
    }
}
