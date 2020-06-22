package watch;

import baidu.translate.TransApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Utils;
import utils.UtilsMini;
import youdao.YoudaoTranslateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-11-18
 * @see
 * @since
 */
public class EnglishSpider {
    private static final List<String> words = Utils.readFileToList("Q:\\phrase.txt");
    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer

    public static void main(String[] args) {
//        getBaiduTranslateApi();
        getYoudaoTranslateApi();
//        getZhFromYoudao();
//        processYoudaoResult();
//        System.out.println(requestYoudaoApi("good"));
    }

    /**
     * 调用有道，翻译
     */
    private static void getYoudaoTranslateApi() {
        int count = 0;
        for (String yingyu : words) {
            String result = "";
            try {
                Thread.sleep(150);
                result = YoudaoTranslateUtils.requestYoudaoApi(yingyu);
                System.out.println(++count);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Utils.writeToTxt("Q:\\phrase_zh_youdao.txt", yingyu + "\t" + result);
        }
    }
    /**
     * 调用百度，翻译
     */
    private static void getBaiduTranslateApi() {
        int count = 0;
        for (String yingyu : words) {
            String result = "";
            try {
                Thread.sleep(1050);
                result = requestBaiduApi(yingyu);
                System.out.println(++count);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Utils.writeToTxt("Q:\\phrase_zh_baidu.txt", yingyu + "\t" + result);
        }
    }


    /**
     * 调用百度api
     *
     * @param word
     * @return
     */
    private static String requestBaiduApi(String word) {
        final String APP_ID = "20191118000358247";
        final String SECURITY_KEY = "99JEIjXikaExjT5VJ5hA";
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        try {
            JSONObject jsonObject = JSON.parseObject(api.getTransResult(word, "en", "zh"));
            return jsonObject.getJSONArray("trans_result").getJSONObject(0).getString("dst").trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从有道上获取词性
     */
    private static void getCixingFromYoudao() {
        for (String word : words) {
            try {
                Document document = Jsoup.connect("http://dict.youdao.com/w/" + word + "/").timeout(5000).get();
                String cixing = document.getElementsByClass("trans-container").text().trim();
                Utils.writeToTxt("Q:\\cixing.txt", word + "\t" + cixing);
            } catch (IOException e) {
                e.printStackTrace();
                Utils.writeToTxt("Q:\\cixing.txt", word + "\t");
            }
        }
    }

    /**
     * 从有道上获取词性
     */
    private static void getZhFromYoudao() {
        for (String word : words) {
            try {
                Document document = Jsoup.connect("http://dict.youdao.com/w/" + word + "/").timeout(5000).get();
                // 判断是否操作纠错
                String errorInfo = document.select(".typo-rel a").text().trim();
                String zh = document.getElementsByClass("trans-container").text().trim();
                Utils.writeToTxt("Q:\\phrash_zh_youdao.txt", word + "\t" + zh+"\t"+errorInfo);
            } catch (IOException e) {
                e.printStackTrace();
                Utils.writeToTxt("Q:\\phrash_zh_youdao.txt", word + "\t");
            }
        }
    }

    /**
     * 从有道上获取单词的短语
     */
    private static void getDuanyuFromYoudao() {
        for (String word : words) {
            try {
                Document document = Jsoup.connect("http://dict.youdao.com/w/" + word + "/").timeout(5000).get();
                Elements elements = document.select("div#webPhrase a");
                List<String> duanyus = new ArrayList<>();
                for (Element element : elements) {
                    System.out.println(word);
                    String duanyu = element.text().trim();
                    if (StringUtils.isBlank(duanyu)) {
                        continue;
                    }
                    if (!duanyu.toLowerCase().contains(word.toLowerCase())) {
                        continue;
                    }
                    duanyus.add(duanyu);
                }
                Utils.writeToTxt("Q:\\duanyu.txt", word + "\t" + StringUtils.join(duanyus.toArray(), "|"));
            } catch (IOException e) {
                e.printStackTrace();
                Utils.writeToTxt("Q:\\duanyu.txt", word + "\t");
            }
        }
    }

    /**
     * 从有道上爬取音标
     */
    private static void getPronFromYoudao() {
        List<String> words = Utils.readFileToList("Q:\\watch\\yingyu.txt");
        for (String word : words) {
            try {
                Document document = Jsoup.connect("http://dict.youdao.com/w/" + word + "/").timeout(5000).get();
                Elements elements = document.getElementsByClass("pronounce");
                String pron = "";
                for (Element element : elements) {
                    if (element.text().contains("英")) {
                        pron += element.text().replace("英", "").trim();
                        pron += "\t";
                    }
                }
                Utils.writeToTxt("Q:\\pron.txt", word + "\t" + pron.trim());
            } catch (IOException e) {
                e.printStackTrace();
                Utils.writeToTxt("Q:\\pron.txt", word);
            }
        }
    }
}
