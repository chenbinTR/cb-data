package watch;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ChenOT
 * @Description: TODO
 * @date 2020/10/27
 */
public class WatchSpider {
    public static void main(String[] args) {
//        baiduIdiom();
        get911();
//        filter911();
    }

    private static void baiduIdiom() {
        List<String> idioms = Utils.readFileToList("E:\\成语.txt");
        String url = "https://hanyu.baidu.com/s?wd=%s&ptype=zici";
        for (String idiom : idioms) {
            String urlNew = String.format(url, URLEncoder.encode(idiom));
            Document document = null;
            try {
                Thread.sleep(RandomUtil.randomInt(500, 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                document = Jsoup.connect(urlNew).get();
            } catch (IOException e) {
                e.printStackTrace();
                Utils.writeToTxt("E:\\error_idiom.txt", idiom);
                continue;
            }
            try {
                // 成语
                String basic1 = document.select("#pinyin strong").text().trim();
                // 拼音
                String basic2 = document.getElementById("pinyin").getElementsByTag("b").text().trim();
                // 解释
                String basic3 = document.select("#basicmean-wrapper p").text().trim();
                // 出处
                String basic4 = document.select("#source-wrapper .tab-content").text().trim();
                // 例句
                String basic5 = document.select("#liju-wrapper .tab-content").text().trim();
                // 近义词
                String basic6 = document.select("#synonym .block").text().trim();
                // 反义词
                String basic61 = document.select("#antonym .block").text().trim();
                // 典故
                String basic7 = document.select("#story-wrapper .tab-content").text().trim();

                // 保存基本解释
                Utils.writeToTxt("E:\\baidu_idiom_basic.txt", basic1 + "\t" + basic2 + "\t" + basic3 + "\t" + basic4 + "\t" + basic5 + "\t" + basic6 + "\t" + basic61 + "\t" + basic7);

                Elements elements = document.select("#detailmean-wrapper li");
                // 解释
                String detail3 = "";
                // 出自
                String detail4 = "";
                // 示例
                String detail5 = "";
                // 语法
                String detail6 = "";
                for (Element element : elements) {
                    if (element.text().indexOf("【解释】") > -1) {
                        detail3 = element.text();
                    }
                    if (element.text().indexOf("【出自】") > -1) {
                        detail4 = element.text();
                    }
                    if (element.text().indexOf("【示例】") > -1) {
                        detail5 = element.text();
                    }
                    if (element.text().indexOf("【语法】") > -1) {
                        detail6 = element.text();
                    }
                }
                // 出处
                String detail7 = basic4;
                // 例句
                String detail8 = basic5;
                // 近义词
                String detail9 = basic6;
                // 反义词
                String detail91 = basic61;
                // 典故
                String detail10 = basic7;

                // 保存基本解释
                Utils.writeToTxt("E:\\baidu_idiom_detail.txt", basic1 + "\t" + basic2 + "\t" + detail3 + "\t" + detail4 + "\t" + detail5 + "\t" + detail6 + "\t" + detail7 + "\t" + detail8 + "\t" + detail9 + "\t" + detail91 + "\t" + detail10);
            } catch (Exception e) {
                e.printStackTrace();
                Utils.writeToTxt("E:\\error_idiom.txt", idiom);
            }

        }
    }

    /**
     * 911成语大全
     */
    private static void get911() {
        String url = "https://chengyu.911cha.com/?q=%s";
        List<String> idioms = Utils.readFileToList("E:\\left.txt");
        for (String idiom : idioms) {
            if(idiom.length() == 5){
                idiom = idiom.substring(1);
            }
            String urlNew = String.format(url, URLEncoder.encode(idiom));
            Document document = null;
            try {
                Thread.sleep(RandomUtil.randomInt(500, 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                document = Jsoup.connect(urlNew)
                        .header("Host","chengyu.911cha.com")
                        .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0")
                        .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .header("Accept-Encoding","gzip, deflate, br")
                        .header("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                        .header("Connection","keep-alive")
                        .header("Content-Length","0")
                        .cookie("Hm_lpvt_2e69b379c7dbfdda15f852ee2e7139dc\t","1603852714")
                        .cookie("Hm_lvt_2e69b379c7dbfdda15f852ee2e7139dc\t","1603781248")
                        .post();
            } catch (IOException e) {
                e.printStackTrace();
                Utils.writeToTxt("E:\\error_911_idiom.txt", idiom);
                continue;
            }
            Elements elements = document.select(".mcon tr");
            boolean isMatch = false;
            for (Element element : elements) {
                String chengyu = element.select("a").select(".green").text().trim();
                System.out.println(chengyu+"\t"+idiom);
                if (chengyu.equals(idiom)) {
                    String detailUrl = "https://chengyu.911cha.com/"+element.select("a").attr("href").trim().replace("./","");
                    getDetail(detailUrl, idiom);
                    isMatch = true;
                    break;
                }
            }
            if(!isMatch){
                Utils.writeToTxt("E:\\error_911_idiom.txt", idiom);
            }
        }
    }
    /**
     * 911成语大全
     */
    private static void getDetail(String url, String idiom) {
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .header("Host","chengyu.911cha.com")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:81.0) Gecko/20100101 Firefox/81.0")
                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding","gzip, deflate, br")
                    .header("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
                    .header("Connection","keep-alive")
                    .header("Content-Length","0")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
            Utils.writeToTxt("E:\\error_911_idiom.txt", idiom);
            return;
        }
        String ci = document.select(".mcon h2").text().trim();
        if (!idiom.equals(ci)) {
            Utils.writeToTxt("E:\\error_911_idiom.txt", idiom);
            return;
        }
        // 拼音
        String pinyin = document.select(".f16").text().trim();

        //详细信息
        Elements elements = document.select(".f14 p");
        JSONObject jsonObject = new JSONObject();
        for (Element element : elements) {
            String key = element.select(".tt").text().trim();
            String value = element.text().replace(key,"").trim();
            if (StringUtils.isAnyBlank(key, value)) {
                continue;
            }
            jsonObject.put(key, value);
        }
        Utils.writeToTxt("E:\\911_idiom.txt", idiom, pinyin, jsonObject.toJSONString());
    }

    private static void filter911(){
//        List<String> idioms = Utils.readFileToList("E:\\成语.txt");
        List<String> idioms111 = Utils.readFileToList("E:\\911_idiom_1.txt");
        Set<String> keys = new HashSet<>();
        for (String s : idioms111) {
            JSONObject item = JSONObject.parseObject(s.split("\t")[2]);
            keys.addAll(item.keySet());
        }
        keys.forEach(e-> System.out.println(e));
        Utils.writeToTxt("E:\\911_idiom.txt", "成语", "拼音", StringUtils.join(keys.toArray(),"\t"));
        for (String s : idioms111) {
            String chengyu = s.split("\t")[0];
            String pinyin = s.split("\t")[1];
            JSONObject item = JSONObject.parseObject(s.split("\t")[2]);

            List<String> list = new ArrayList<>(keys.size());
            keys.forEach(e->{
                list.add(StringUtils.isBlank(item.getString(e))?"":item.getString(e));
            });
            Utils.writeToTxt("E:\\911_idiom.txt", chengyu, pinyin, StringUtils.join(list.toArray(),"\t"));
        }
    }
}
