package ask.sogou;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.UtilsMini;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-11-28
 * @see
 * @since
 */
public class SogoContentProcessor {
    private static String path = "/mnt/work/snd-faq/chenbin/classes/ask/sogou/";
//    private static String path = "Q:\\ask\\";
    private static String urlFile = "sogo_helth_url.txt";
    private static String contentFile = "sogo_helth_url_content.txt";

    private static List<String> urlList;
    static{
        urlList = UtilsMini.readFileToList(path+urlFile);
    }

    public static void main(String[] args) {
        process();
    }
    private static void process(){
        int count = 0;
        for(String urlContent:urlList){
            ++count;
            SogoUrl sogoUrl = JSONObject.parseObject(urlContent, SogoUrl.class);
            if(sogoUrl.getInfos().contains("0个回答")){
                continue;
            }
            String url = String.format("https://wenwen.sogou.com%s", sogoUrl.getUrl());
            SogoContent sogoContent = getSogouWeb(url);
            if(sogoContent == null || CollectionUtils.isEmpty(sogoContent.getAnswerInfos())){
                try {
                    Thread.sleep(60*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            sogoContent.setUrl(sogoUrl.getUrl());
            sogoContent.setTags(sogoUrl.getTags());
            sogoContent.setInfos(sogoUrl.getInfos());
            System.out.println(String.format("%d 有结果：%s", count, url));
            UtilsMini.writeToTxt(path+contentFile, JSONObject.toJSONString(sogoContent));
        }
    }

    /**
     * 抓取单个页面
     * @param url
     * @return
     */
    private static SogoContent getSogouWeb(String url){
        try {
            SogoContent sogoContent = new SogoContent();
            Document document = Jsoup.connect(url).timeout(5000).get();
            Elements elements = document.getElementsByAttributeValueContaining("class", "replay-section answer_item");
            List<SogoContent.AnswerInfo> answerInfos = new ArrayList<>();
            String title = document.getElementById("question_title_val").text();
            for(Element element:elements){
                // 回答时间
                String time = element.getElementsByClass("user-txt").text().trim();
                // 是否被采纳
                String toprgt = element.getElementsByClass("toprgt-bar").text().trim();
                // 答案
                String content = element.getElementsByAttributeValueContaining("class", "replay-info-txt answer_con").text().trim();
                // 赞的个数
                String like = element.getElementsByClass("txt-num").text().trim();

                if(StringUtils.isNotBlank(content)){
                    SogoContent.AnswerInfo answerInfo = sogoContent.new AnswerInfo(content,like,time,toprgt );
                    answerInfos.add(answerInfo);
                }
            }
            sogoContent.setAnswerInfos(answerInfos);
            sogoContent.setTitle(title);
            return sogoContent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
