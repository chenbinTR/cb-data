package ask.sogou;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.UtilsMini;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author ChenOT
 * @date 2019-11-28
 * @see
 * @since
 */
public class SogoProcessor {
    private static String path = "/mnt/work/snd-faq/chenbin/classes/ask/sogou/sogo_medical_url.txt";
    private static boolean isSleepLong = false;
    private static Set<String> URL_SET = new HashSet<>();
    static{
        List<String> list = UtilsMini.readFileToList(path);
        for(String item:list){
            URL_SET.add(JSONObject.parseObject(item).getString("url"));
        }
    }
    public static void main(String[] args) {
        getQuestionUrl();
    }
    private static void getQuestionUrl(){
        String pageUrl = "https://wenwen.sogou.com/cate/tag?tag_id=111&tp=0&pno=%d&ch=ww.fly.fy50#questionList";
        int errorCount = 0;
        for(int i=0; i<Integer.MAX_VALUE; i++){
            String url = String.format(pageUrl, i);
            try {
                if(errorCount>100){
                    System.out.println("爬取暂停： "+i);
                    break;
                }
                if(isSleepLong){
                    Thread.sleep(10000);
                }
                Document document = Jsoup.connect(url).timeout(5000).get();
                Elements elements = document.select(".sort-lst li");
                if(null == elements || elements.size() == 0){
                    System.out.println(String.format("没结果，页数：%d", i));
                    errorCount++;
                }else{
                    System.out.println("有结果，页数："+i);
                }
                for(Element ele:elements){
                    // 链接
                    String href = ele.select("a").attr("href");
                    // tag
                    Elements tags = ele.getElementsByClass("sort-tag");
                    List<String> tagList = new ArrayList<>();
                    for(Element tag:tags){
                        tagList.add(tag.text());
                    }
                    // info
                    Elements infos = ele.getElementsByClass("sort-rgt-txt");
                    List<String> infoList = new ArrayList<>();
                    for(Element info:infos){
                        infoList.add(info.text());
                    }
                    if(StringUtils.isNotBlank(href) && !URL_SET.contains(href)){
                        UtilsMini.writeToTxt(path, JSONObject.toJSONString(new SogoUrl(href, tagList, infoList)));
                        URL_SET.add(href);
                        errorCount = 0;
                        isSleepLong = false;
                    }
                }
            } catch (Exception e) {
                System.out.println(i);
                if(e instanceof HttpStatusException){
                    isSleepLong = true;
                }
                e.printStackTrace();
            }

        }
    }
}
