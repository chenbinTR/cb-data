package dict;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.HASH;
import org.apache.commons.collections4.MapUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChenOT
 * @Date 2021/4/2
 */
public class Spider {

    public static void main(String[] args) {

        get1();
//        try {
//            Document document = Jsoup.connect("https://so.gushiwen.org/shiwens/default.aspx?astr=%e5%bc%a0%e5%85%83%e5%b9%b2").get();
//            Elements elements = document.select("div#leftZhankai div.sons");
//            for (Element element : elements) {
//                System.out.println(element.text());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void get1() {
        String url = "https://so.gushiwen.org/%s";
        String zhushiUrl = "https://so.gushiwen.org/nocdn/ajaxshiwencont.aspx?id=%s&value=zhu";
        String shangxiUrl = "https://so.gushiwen.org/nocdn/ajaxshiwencont.aspx?id=%s&value=shang";
        String yiwenUrl = "https://so.gushiwen.org/nocdn/ajaxshiwencont.aspx?id=%s&value=yi";
        List<String> authorUrlList =  Utils.readFileToList("E:\\DICT_DATA\\入库\\诗人列表.txt");
        for (String authorUrl : authorUrlList) {
            try {
                Document document = Jsoup.connect(authorUrl).timeout(10000).get();
                Elements elements = document.select("div.typecont span a");
                Map<String, String> urlMap = new HashMap<>();
                for (Element element : elements) {
                    urlMap.put(element.attr("href"), element.text().trim());
                }
                if (MapUtils.isEmpty(urlMap)) {
//                continue;
                }
                urlMap.forEach((k, v) -> {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        String contentUrl = String.format(url, k);
                        String id = k.replace("/shiwenv_", "").replace(".aspx", "");
                        jsonObject.put("id", id);
                        // 获取content
                        Element elementContent = Jsoup.connect(contentUrl).timeout(5000).get().select("div#sonsyuanwen").get(0);
                        String author = elementContent.select("p.source").text();
                        jsonObject.put("author", author);
                        String content = elementContent.select("div.contson").text();
                        jsonObject.put("content",content);
                        // 获取译文
                        String yiUrl = String.format(yiwenUrl, id);
                        jsonObject.put("yiwen", Jsoup.connect(yiUrl).timeout(5000).get().html());
                        jsonObject.put("yiUrl", yiUrl);
                        // 获取注释
                        String zhuUrl = String.format(zhushiUrl, id);
                        jsonObject.put("zhushi", Jsoup.connect(zhuUrl).timeout(5000).get().html());
                        jsonObject.put("zhuUrl", zhuUrl);
                        // 获取赏析
                        String shangUrl = String.format(shangxiUrl, id);
                        jsonObject.put("shangxi", Jsoup.connect(shangUrl).timeout(3000).get().html());
                        jsonObject.put("shangUrl", shangUrl);

                        Utils.writeToTxt("E:\\DICT_DATA\\入库\\gushiciwang.txt", jsonObject.toJSONString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
