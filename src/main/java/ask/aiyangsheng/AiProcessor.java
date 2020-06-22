//package ask.aiyangsheng;
//
//import com.alibaba.fastjson.JSONObject;
//import com.turing.spider.j8.Java8Tester;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import utils.UtilsMini;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author ChenOT
// * @date 2019-12-13
// * @see
// * @since
// */
//public class AiProcessor {
//    public static void main(String[] args) {
//        String url = "https://www.155166.cn/%d.html";
//        for (int i = 1; i > 0; i--) {
//            try {
//                AiEntity aiEntity = getPageContent("http://www.ttys5.com/shanshi/yingyang/2018-10-30/160744.html");
//                if(aiEntity != null){
//                    UtilsMini.writeToTxt("/mnt/work/snd-faq/chenbin/aiyangsheng.txt", JSONObject.toJSONString(aiEntity));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println(i);
//                UtilsMini.writeToTxt("/mnt/work/snd-faq/chenbin/aiyangsheng_error.txt", e.getMessage()+"\t"+i);
//            }
//        }
//    }
//
//    private static AiEntity getPageContent(String url) throws IOException {
//        Document document = Jsoup.connect(url).timeout(2000).get();
//        String tag = document.select(".dq a").text();
//        List<String> tags = new ArrayList<>();
//        if (StringUtils.isNotBlank(tag)) {
//            tag = tag.replace("首页", "").trim();
//            tags.addAll(Arrays.asList(tag.split(" ")));
//        }
//        String question = document.select(".content h1").text();
//        if (StringUtils.isBlank(question)) {
//            return null;
//        }
//        String answer = document.select(".content p").text();
//        if (StringUtils.isBlank(answer)) {
//
//            return null;
//        }
//        return new AiEntity(answer, question, tags);
//
//    }
//}
