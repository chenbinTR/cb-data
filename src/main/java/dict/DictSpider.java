package dict;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ChenOT
 * @Date 2021/4/6
 */
public class DictSpider {
    public static void main(String[] args) {
        Set<String> urls = Utils.readFileToSet("E:\\dict_url.txt");
        for (String url : urls) {
            try {
                Document document = Jsoup.connect(url).timeout(5000).get();
                Elements elementsCi = document.select("div.layout.nfo ul");
                Elements elementsName = document.select("div.layout.nfo div");
                if (elementsCi == null || elementsCi.size() < 1) {
                    continue;
                }
                int i = 0;
                List<BaseCnEn> fanyiciList = new ArrayList<>();
                List<BaseCnEn> jinyiciList = new ArrayList<>();
                for (Element element : elementsCi) {
                    String name = elementsName.get(i).text();

                    if (name.indexOf("近义词") > -1) {
                        // 近义词
                        Elements lis = element.select("li");
                        for (Element li : lis) {
                            String en = li.select("a").get(0).text().trim();
                            String cn = li.ownText();
                            BaseCnEn baseCnEn = new BaseCnEn();
                            baseCnEn.setCn(cn);
                            baseCnEn.setEn(en);
                            jinyiciList.add(baseCnEn);
                        }
                    }
                    if (name.indexOf("反义词") > -1) {
                        // 反义词
                        Elements lis = element.select("li");
                        for (Element li : lis) {
                            String en = li.select("a").get(0).text().trim();
                            String cn = li.ownText();
                            BaseCnEn baseCnEn = new BaseCnEn();
                            baseCnEn.setCn(cn);
                            baseCnEn.setEn(en);
                            fanyiciList.add(baseCnEn);
                        }
                    }
                    i++;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("url", url);
                jsonObject.put("fanyici", fanyiciList);
                jsonObject.put("jinyici", jinyiciList);

                Utils.writeToTxt("E:\\dict_s_a.txt", jsonObject.toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
