package chat;

import utils.Utils;

/**
 * @author ChenOT
 * @Description: TODO
 * @date 2021/1/5
 */
public class ClearRedis {
    public static void main(String[] args) {
        String url = "http://47.94.58.57:8999/chat/delredis?redisKey=dialogue:";
        String qid = "2064165|2070764|2098034|2169118|2183174|2183349|2181458|2189756|2195054|2207788|2210497|2222097|2231518|2235068|2236747|2256216|2275888|2288707|2289589|2294639|2307174|2314310|2315945|2319638|2098596|3285385|2155551|2349803|2714040|2304036|2321531";

        String[] qids = qid.split("\\|");
        for (String s : qids) {
            String reqUrl = url+s;
            System.out.println(Utils.httpGet(reqUrl));
        }
    }
}
