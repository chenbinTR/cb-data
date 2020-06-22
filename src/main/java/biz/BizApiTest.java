package biz;

import utils.Utils;

import java.util.Date;
import java.util.List;

/**
 * @author ChenOT
 * @date 2020-04-24
 * @seee
 * @since
 */
public class BizApiTest {
    public static void main(String[] args) {
        String url = "http://api.turingos.cn/turingos/api/v2";
        String apikey = "e4259120e7c240fab6a3d48a3b7e62f7";
        String reqData = "{\"data\":{\"content\":[{\"data\":\"%s\"}],\"userInfo\":{\"uniqueId\":\"123456\"}},\"key\":\"40fb8ea1262f415384cebd86b3a066a6\",\"timestamp\":\"%s\"}";
        List<String> datas = Utils.readFileToList("Q:\\"+apikey+".txt");
        for(String data:datas){
            String result = "";
            try{
                Thread.sleep(100);
                result = Utils.httpPost(String.format(reqData, data, System.currentTimeMillis()+""),url);
            }catch (Exception e){
                e.printStackTrace();
            }
            Utils.writeToTxt("Q:\\"+apikey+"-result.txt", data+"\t"+result);
        }
    }
}
