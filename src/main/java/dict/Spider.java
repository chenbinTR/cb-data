package dict;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.HASH;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Utils;

import java.io.*;
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
//        process();
    }

    private static void get2() {
        File file = new File("E:\\DICT_DATA\\入库\\古诗\\古诗词网.txt");
        File file1 = new File("E:\\DICT_DATA\\入库\\古诗\\shangxi.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "utf-8"))) {
                String line;
                while (null != (line = reader.readLine())) {
                    if (StringUtils.isNotBlank(line.trim())) {
                        JSONObject jsonObject = JSONObject.parseObject(line.trim());
                        String shangUrl = jsonObject.getString("shangUrl");
                        System.out.println(shangUrl);

                        String id = jsonObject.getString("id");
                        JSONObject jsonNew = new JSONObject();
                        jsonNew.put("id", id);
                        jsonNew.put("shangxi", packageCon(shangUrl).timeout(10000).get());

                        FileUtil.appendString(jsonNew.toJSONString() + "\r\n", file1, "UTF-8");

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void process() {
        List<String> lines = Utils.readFileToList("E:\\DICT_DATA\\入库\\gushiciwang.txt");
        int countYiwen = 0;
        int countYiwen2 = 0;
        int countZhushi = 0;
        int countZhushi2 = 0;
        int countShangxi = 0;
        int countShangxi2 = 0;
        int countAll = 0;
        for (String line : lines) {
            JSONObject jsonObject = JSON.parseObject(line);
            String yiwen = jsonObject.getString("yiwen");
            String zhushi = jsonObject.getString("zhushi");
            String shangxi = jsonObject.getString("shangxi");
            if (yiwen.indexOf("未登录") > -1) {
                countYiwen++;
            } else {
                countYiwen2++;
            }
            if (zhushi.indexOf("未登录") > -1) {
                countZhushi++;
            } else {
                countZhushi2++;
            }
            if (shangxi.indexOf("未登录") > -1) {
                countShangxi++;
            } else {
                countShangxi2++;
            }
            countAll++;
        }

        System.out.println("countYiwen \t" + countYiwen);
        System.out.println("countYiwen2 \t" + countYiwen2);
        System.out.println("countZhushi \t" + countZhushi);
        System.out.println("countZhushi2 \t" + countZhushi2);
        System.out.println("countShangxi \t" + countShangxi);
        System.out.println("countShangxi2 \t" + countShangxi2);
        System.out.println("countAll \t" + countAll);
    }

    private static void get1() {
        String url = "https://so.gushiwen.org/%s";
        String zhushiUrl = "https://so.gushiwen.org/nocdn/ajaxshiwencont.aspx?id=%s&value=zhu";
        String shangxiUrl = "https://so.gushiwen.org/nocdn/ajaxshiwencont.aspx?id=%s&value=shang";
        String yiwenUrl = "https://so.gushiwen.org/nocdn/ajaxshiwencont.aspx?id=%s&value=yi";
        String userAgentValue = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36";
        List<String> authorUrlList = Utils.readFileToList("E:\\DICT_DATA\\入库\\诗人列表.txt");
        for (String authorUrl : authorUrlList) {
            try {
                Document document = Jsoup.connect(authorUrl).timeout(10000).get();
                Elements elements = document.select("div.typecont span a");
                Map<String, String> urlMap = new HashMap<>();
                for (Element element : elements) {
                    urlMap.put(element.attr("href"), element.text().trim());
                }
                if (MapUtils.isEmpty(urlMap)) {
                    continue;
                }
                urlMap.forEach((k, v) -> {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        String contentUrl = String.format(url, k);
                        String id = k.replace("/shiwenv_", "").replace(".aspx", "");
                        jsonObject.put("id", id);
                        // 获取content
//                        Connection con1 = packageCon(contentUrl);
//                        Element elementContent = Jsoup.connect(contentUrl).get().select("div#sonsyuanwen").get(0);
//                        String author = elementContent.select("p.source").text();
//                        jsonObject.put("author", author);
//                        String content = elementContent.select("div.contson").text();
//                        jsonObject.put("content", content);
                        // 获取译文
//                        String yiUrl = String.format(yiwenUrl, id);
//                        Connection con2 = packageCon(yiUrl);
//                        jsonObject.put("yiwen", con2.get().html());
//                        jsonObject.put("yiUrl", yiUrl);
                        // 获取注释
//                        String zhuUrl = String.format(zhushiUrl, id);
//                        Connection con3 = packageCon(zhuUrl);
//                        jsonObject.put("zhushi", con3.get().html());
//                        jsonObject.put("zhuUrl", zhuUrl);
                        // 获取赏析
                        String shangUrl = String.format(shangxiUrl, id);
                        Connection con4 = packageCon(contentUrl);
                        jsonObject.put("shangxi", con4.get().html());
                        jsonObject.put("shangUrl", shangUrl);

                        Utils.writeToTxt("E:\\DICT_DATA\\入库\\古诗\\shangxi.txt", jsonObject.toJSONString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Connection packageCon(String url) {
        // 请求头
        Connection connection = Jsoup.connect(url).timeout(5000);
        connection.header("authority","so.gushiwen.cn");
        connection.header("Accept", "*/*");
        connection.header("Accept-Encoding", "gzip, deflate");
        connection.header("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        connection.header("Connection", "keep-alive");
        connection.header("Cookie", "Hm_lvt_9007fab6814e892d3020a64454da5a55=1617873232; ASP.NET_SessionId=e4bmkqkjapyrbhmxs503bfe3; login=flase; codeyzgswso=c8d8919c77a0da5f; gsw2017user=1746953|6A5471B38CFFFF27880E4F7E9679CF7A; login=flase; wxopenid=ouY_U1cSKoR4OKekopUyXi-OwFhQ; Hm_lpvt_9007fab6814e892d3020a64454da5a55=1617878720");
        connection.header("Host", "so.gushiwen.org");
        connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
        return connection;
    }
}
