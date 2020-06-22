package ask.aiyangsheng;

import ask.cainiuyangsheng.Data;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import utils.UtilsMini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 爬取彩牛养生网站数据
 *
 * @author ChenOT
 * @date 2019-11-28
 * @see
 * @since
 */
public class AiYangShengPageProcessor implements PageProcessor {
    private static String path = "Q:\\";
    /**
     * 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
      */
    private Site site = Site.me().setRetryTimes(0).setSleepTime(0);

    private static boolean tag = true;
    public static void main(String[] args) {
        Spider.create(new AiYangShengPageProcessor())
                .addUrl("http://www.ttys5.com/shanshi/yingyang/2018-10-30/160744.html")
//                .addPipeline(new JsonFilePipeline("Q:\\webmagic"))
                .thread(1)
                .run();
    }

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     *
     * @param page
     */
    @Override
    public void process(Page page) {
//        // 获取位置分类
//        String position = page.getHtml().getDocument().getElementsByClass("weizhi").text();
//        // 获取正文
//        List<String> contents = new ArrayList<>();
//        Elements elements = page.getHtml().getDocument().select(".reads p");
//        for(Element element:elements){
//            contents.add(element.text());
//        }
//        // 获取标题
//        String title = page.getHtml().getDocument().select(".readbox h1").text();
//        if(StringUtils.isNotBlank(title) && CollectionUtils.isNotEmpty(contents)){
//            UtilsMini.writeToTxt(path+"cainiu.txt", JSONObject.toJSONString(new Data(position, title, contents, null)));
//        }
        Document document = page.getHtml().getDocument();
        String tag = document.select(".dq a").text();
        List<String> tags = new ArrayList<>();
        if (StringUtils.isNotBlank(tag)) {
            tag = tag.replace("首页", "").trim();
            tags.addAll(Arrays.asList(tag.split(" ")));
        }
        String question = document.select(".content h1").text();
        String answer = document.select(".content p").text();
        if (StringUtils.isNotBlank(question) && StringUtils.isNotBlank(answer)) {
//            return null;
            int index = answer.indexOf("来源：");
            if(index>-1){
                answer = answer.substring(index+3).trim().replace(" ","").replace("　","");
            }
            index = answer.indexOf("天天养生提供");
            if(index>-1){
                answer = answer.substring(0, index).trim();
            }
            UtilsMini.writeToTxt(path+"tiantianyangsheng.txt", JSONObject.toJSONString(new AiEntity(answer, question, tags)));
        }
//        page.addTargetRequests(page.getHtml().links().regex("(https://www\\.155166\\.cn/\\d+\\.html)").all());
        page.addTargetRequests(page.getHtml().links().regex("(http://www\\.ttys5\\.com/[a-z]+/[a-z]+/\\d+-\\d+-\\d+/\\d+.html)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
