package watch;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author ChenOT
 * @date 2019-11-15
 * @see
 * @since
 */
public class ChineseSpider {
    private static final Map<String, String> infoMap = new HashMap<>();
    private static final List<String> hanzis = Utils.readFileToList("Q:\\watch\\hanzi2-原始.txt");

    public static void main(String[] args) {
        // 获取拼音、部首
//        getPinyinAndBushouFormBaidu();
        // 拼音拆分
//        pinyinSplit();
        // 根据多音字，获取组词
//        getZuciFromBaidu();
        // 组词过滤
//        filterZuci();
        // 获取查询网的笔画
        getBihuaFromChaxunwang();
    }

    private static void filterZuci(){
        List<String> list = Utils.readFileToList("Q:\\watch\\hanzi2_组词_全部.txt");
        for(String line:list){
            String[] items = line.split("\t");
            if(items.length == 3){
                List<String> nZ = new ArrayList<>();
                String[] zucis = items[2].split("\\|");
                for(String zuci:zucis){
                    if(zuci.length()>4 || zuci.length()<2){

                    }else{
                        nZ.add(zuci);
                    }
                }
                Utils.writeToTxt("Q:\\watch\\hanzi2_组词_过滤.txt", items[0] + "\t" + items[1]+"\t"+StringUtils.join(nZ.toArray(),"|"));
            }else{
                Utils.writeToTxt("Q:\\watch\\hanzi2_组词_过滤.txt", line);
            }
        }
    }
    /**
     * 从查询网获取笔画名称
     */
    private static void getBihuaFromChaxunwang() {
        String result = null;
        for (String word : hanzis) {
            try {
                Thread.sleep(10000);
                String url = String.format("https://bihua.51240.com/%s__bihuachaxun/", URLEncoder.encode(word).replace("%", "").toLowerCase());
                Document document = Jsoup.connect(url).timeout(3000)
//                            .proxy(ip, Integer.valueOf(port))
                        .get();
                Elements elements = document.getElementsByTag("tr");
                for (Element element : elements) {
                    String content = element.text();
                    if (content.indexOf("名称") > -1 && content.indexOf("读音") < 0) {
                        result = content.replace("名称", "").replace("更多：", "").replace("https://www.51240.com/", "").replace(" ", "").trim();
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Utils.writeToTxt("Q:\\watch\\hanzi2_笔画_error.txt", word);
            }
            if (StringUtils.isNotBlank(result)) {
                System.out.println(result);
                Utils.writeToTxt("Q:\\watch\\hanzi2_笔画.txt", word + "\t" + result);
            }else{
                Utils.writeToTxt("Q:\\watch\\hanzi2_笔画_error.txt", word);
            }
        }
    }

    /**
     * 爬取笔划
     */
    private static void getBihua() {
        String url = "https://www.hanzi5.com/bishun/%s.html";
        for (String hanzi : hanzis) {
            String escapeResult = escapes(hanzi);
            String bihua = "";
            if (StringUtils.isNotBlank(escapeResult)) {
                String reqUrl = String.format(url, escapeResult);
                try {
                    Document document = Jsoup.connect(reqUrl).timeout(5000).get();
                    bihua = document.getElementsByClass("hanzi5-article-hanzi-info-td2").text();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Utils.writeToTxt("Q:\\watch\\hanzi_笔划.txt", hanzi + "\t" + bihua);
        }
    }

    /**
     * 汉字转编码
     *
     * @param word
     * @return
     */
    private static String escapes(String word) {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByExtension("js");
        try {
            //直接解析
            String res = (String) engine.eval(" escape('" + word + "')");
            return res.replace("%u", "").toLowerCase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * 根据拼音（多音字），汉字，抓取所有的组词
     *
     * @param pinyin
     * @param hanzi
     * @return
     */
    private static Set<String> getZuci(String pinyin, String hanzi) {
        Set<String> results = new HashSet<>();
        int maxPage = 1000;
        String duoyinUrl = "https://hanyu.baidu.com/hanyu/ajax/search_list?py=%s&ptype=zici_tag&wd=%s组词&pn=%d";
        String danyinUrl = "https://hanyu.baidu.com/hanyu/ajax/search_list?wd=%s组词&from=poem&pn=%d";
        String url = "";

        for (int num = 1; num < maxPage; num++) {
            if (StringUtils.isNotBlank(pinyin)) {
                url = String.format(duoyinUrl, pinyin, hanzi, num);
            } else {
                url = String.format(danyinUrl, hanzi, num);
            }
            try {
                String dS = Utils.httpGet(url);
                JSONObject jsonObject = JSONObject.parseObject(dS);
                //判断是否有数据
                int ret_num = jsonObject.getInteger("ret_num");
                if (ret_num == 0) {
                    break;
                }
                JSONArray ret_array = jsonObject.getJSONArray("ret_array");
                for (int i = 0; i < ret_array.size(); i++) {
                    JSONObject item = ret_array.getJSONObject(i);
                    String name = item.getJSONArray("name").getString(0);
                    results.add(name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    /**
     * 获取百度拼音、部首
     */
    private static void getPinyinAndBushouFormBaidu() {
        String url = "https://hanyu.baidu.com/s?wd=%s&ptype=zici";
        for (String hanzi : hanzis) {
            System.out.println(hanzi);
            if (infoMap.containsKey(hanzi)) {
                Utils.writeToTxt("Q:\\watch\\hanzi2-拼音合并_部首.txt", infoMap.get(hanzi));
                continue;
            }
            try {
                Document document = Jsoup.connect(String.format(url, hanzi)).get();
                Elements elements = document.getElementById("pinyin").getElementsByTag("b");
                List<String> pinyins = new ArrayList<>();
                for (Element element : elements) {
                    pinyins.add(element.text().trim());
                }
                String pinyin = StringUtils.join(pinyins.toArray(), "|");
                String state = document.getElementById("radical").getElementsByTag("span").text().trim();
                infoMap.put(hanzi, hanzi + "\t" + pinyin + "\t" + state);
                Utils.writeToTxt("Q:\\watch\\hanzi2-拼音合并_部首.txt", infoMap.get(hanzi));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据多音字的拼音，获取百度组词
     */
    private static void getZuciFromBaidu() {
        List<String> list = Utils.readFileToList("Q:\\watch\\hanzi2_拼音合并_部首.txt");
        for (String item : list) {
            String[] items = item.split("\t");
            String word = items[0];
            String pinyins = items[1];
            if (pinyins.indexOf("|") > -1) {
                String[] ps = pinyins.split("\\|");
                for (String p : ps) {
                    Set<String> set = getZuci(p, word);
                    Utils.writeToTxt("Q:\\watch\\hanzi2_组词_全部.txt", word + "\t" + p + "\t" + StringUtils.join(set.toArray(), "|"));
                }
            } else {
                Set<String> set = getZuci(null, word);
                Utils.writeToTxt("Q:\\watch\\hanzi2_组词_全部.txt", word + "\t" + pinyins + "\t" + StringUtils.join(set.toArray(), "|"));
            }
        }
    }

    /**
     * 一个汉字，多个读音，拆分
     */
    private static void pinyinSplit() {
        List<String> list = Utils.readFileToList("Q:\\watch\\hanzi2_拼音合并_部首.txt");
        for (String item : list) {
            String[] items = item.split("\t");
            String word = items[0];
            String pinyins = items[1];
            String bushou = items[2];
            if (pinyins.indexOf("|") > -1) {
                String[] ps = pinyins.split("\\|");
                for (String p : ps) {
                    Utils.writeToTxt("Q:\\watch\\hanzi2_拼音分开_部首.txt", word + "\t" + p + "\t" + bushou);
                }
            } else {
                Utils.writeToTxt("Q:\\watch\\hanzi2_拼音分开_部首.txt", item);
            }

        }
    }
}
