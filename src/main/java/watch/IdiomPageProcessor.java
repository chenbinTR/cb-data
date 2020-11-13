package watch;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import utils.Utils;

/**
 * @author ChenOT
 * @Description: TODO
 * @date 2020/11/12
 */
public class IdiomPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public static void main(String[] args) {
        //从"https://github.com/code4craft"开始抓
        Spider.create(new IdiomPageProcessor()).addUrl("http://www.hydcd.com/cy/htm5/yz3403.htm").thread(1).run();
    }

    @Override
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        Document document = Jsoup.parse(page.getHtml().toString());
        Elements elements = document.select("p");
        // 成语名字
        String name = document.select(".Dictcx li").text();
        name = Utils.clenaString(name);
        // 成语解释
        String story = "";
        for (Element element : elements) {
            if(element.text().indexOf("【故事】")>-1){
                story = element.text();
            }
        }
//        String story =
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name")==null){
            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        if(StringUtils.isNotBlank(name) && name.length()>1){
            Utils.writeToTxt("E:\\chengyu.txt", name, story);
        }
        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(http://www\\.hydcd\\.com/cy/.*)").all());

    }

    @Override
    public Site getSite() {
        return site;
    }
}
