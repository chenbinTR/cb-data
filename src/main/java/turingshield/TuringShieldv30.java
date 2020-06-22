package turingshield;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.Utils;

import java.util.List;

/**
 * 3.0 测试
 * @author ChenOT
 * @date 2019-12-31
 * @see
 * @since
 */
public class TuringShieldv30 {
    public static void main(String[] args) {
        List<String> lines = Utils.readFileToList("Q:\\Colleague\\张清\\图灵盾\\测试集100%涉政.txt");
        String result = "";
        for(String line:lines){
            int level = 0;
            try{
                String re = Utils.httpPost(line, "http://192.168.10.20:8087/turing-shield-3");
                JSONObject jsonObject = JSON.parseObject(re);
                JSONObject politicsInfo = jsonObject.getJSONObject("sensitiveInfo").getJSONObject("politicsInfo");
                level = politicsInfo.getInteger("level");
                JSONArray wordsInfo = politicsInfo.getJSONArray("wordsInfo");
                if(level != 1 && wordsInfo.size()>0){
                    level = 2;
                }
            }catch (Exception e){
                e.printStackTrace();
                System.err.println(line);
            }

            Utils.writeToTxt("Q:\\测试集100%涉政_3.0.txt", line+"\t"+level);
        }
    }
}
