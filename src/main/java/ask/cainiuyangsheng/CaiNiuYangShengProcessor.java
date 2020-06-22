package ask.cainiuyangsheng;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.UtilsMini;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬取彩牛养生网站数据
 *
 * @author ChenOT
 * @date 2019-11-28
 * @see
 * @since
 */
public class CaiNiuYangShengProcessor{
    private static String path = "/mnt/work/snd-faq/chenbin/classes/ask/cainiuyangsheng/";
//    private static List<String> urls = new ArrayList<>();
    static{
//        List<String> list = UtilsMini.readFileToList(path+"cainiu.txt");
//        for(String item:list){
//            urls.add(JSONObject.parseObject(item).getString("url"));
//        }
    }
    public static void main(String[] args) {
        process();
    }

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     *
     */
    public static void process() {
        String pageUrl = "https://www.cnys.com/article/%d.html";
        for(int i=1; i<1000000; i++){
            String url = String.format(pageUrl, i);
            try {
                Document document = Jsoup.connect(url).timeout(5000).get();
                // 获取位置分类
                String position = document.getElementsByClass("weizhi").text();
                // 获取正文
                List<String> contents = new ArrayList<>();
                Elements elements = document.select(".reads p");
                for(Element element:elements){
                    contents.add(element.text());
                }
                // 获取标题
                String title = document.select(".readbox h1").text();
                if(StringUtils.isNotBlank(title) && CollectionUtils.isNotEmpty(contents)){
                    UtilsMini.writeToTxt(path+"cainiu.txt", JSONObject.toJSONString(new Data(position, title, contents, url)));
                    System.out.println("有结果："+i);
                }
            } catch (IOException e) {
                System.out.println("error page: "+i);
                e.printStackTrace();
            }
        }
    }
}
