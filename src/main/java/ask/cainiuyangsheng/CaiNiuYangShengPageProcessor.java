package ask.cainiuyangsheng;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import utils.UtilsMini;

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
public class CaiNiuYangShengPageProcessor implements PageProcessor {
    private static String path = "/mnt/work/snd-faq/chenbin/classes/ask/cainiuyangsheng/";
    /**
     * 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
      */
    private Site site = Site.me().setRetryTimes(0).setSleepTime(10);

    private static List<String> urls = new ArrayList<>();;
    static{
        for(int i=10000; i<1000000; i++){
            urls.add(String.format("https://www.cnys.com/shicai/%d.html", i));
        }
    }
    private static boolean tag = true;
    public static void main(String[] args) {
        Spider.create(new CaiNiuYangShengPageProcessor())
                .addUrl("https://www.cnys.com/article/2.html")
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
//        System.out.println(page.getUrl());
//        System.out.println(page.getHtml());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        String position = page.putField("position", page.getHtml().xpath("//h1[@class='weizhi']/a/text()").toString());
//        String position = page.getHtml().xpath("//div[@class='weizhi']/p//a").toString();
        // 获取位置分类
        String position = page.getHtml().getDocument().getElementsByClass("weizhi").text();
        // 获取正文
        List<String> contents = new ArrayList<>();
        Elements elements = page.getHtml().getDocument().select(".reads p");
        for(Element element:elements){
            contents.add(element.text());
        }
        // 获取标题
        String title = page.getHtml().getDocument().select(".readbox h1").text();
        if(StringUtils.isNotBlank(title) && CollectionUtils.isNotEmpty(contents)){
            UtilsMini.writeToTxt(path+"cainiu.txt", JSONObject.toJSONString(new Data(position, title, contents, null)));
        }
        UtilsMini.writeToTxt(path+"cainiu_url.txt", page.getUrl().get());
//        if (page.getResultItems().get("name")==null){
        //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        if(tag){
            System.out.println("add default urls");
            page.addTargetRequests(urls);
            tag = false;
        }
        page.addTargetRequests(page.getHtml().links().regex("(https://www\\.cnys\\.com/article/\\d+\\.html)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
