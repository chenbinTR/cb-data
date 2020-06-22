package baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-06-12
 * @see
 * @since
 */
public class BaiduChatUtil {
    private static String CHAT_URL = "https://aip.baidubce.com/rpc/2.0/unit/service/chat?access_token=%s";
    private static final String ACCESS_TOKEN = "24.fb1c038f7394fb801d81fccd9e3db322.2592000.1566116462.282335-16494883";

    public static void main(String[] args) {
//        System.out.println(getBaiduChatResult("你好"));
        List<String> questions = Utils.readFileToList("Q:\\2.txt");
//        List<String> questions = Arrays.asList("为何要嘎嘎嘎");
        List<String> result = new ArrayList<>();
        for(String question:questions){
            try{
                System.out.println(question);
                result.clear();
                result.add(question);
                Thread.sleep(System.currentTimeMillis()%1000);
                JSONObject jsonObject = getBaiduChatResult(question);
                if(null != jsonObject){
                    JSONArray response_list = jsonObject.getJSONObject("result").getJSONArray("response_list");
                    for(int i = 0; i<response_list.size(); i++){
                        JSONArray action_list = response_list.getJSONObject(i).getJSONArray("action_list");
                        for(int j=0; j<action_list.size(); j++){
                            System.out.println(action_list.getJSONObject(j).getString("say"));
                            result.add(action_list.getJSONObject(j).getString("say"));
//                        result.add(action_list.getJSONObject(j).getString("confidence"));
//                        result.add(action_list.getJSONObject(j).getString("type"));
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if(CollectionUtils.isNotEmpty(result)){
                Utils.writeToTxt("Q:\\1.txt", StringUtils.join(result.toArray(), "\t"));
            }
        }
    }

    /**
     * 调用百度对话接口
     * @param question
     * @return
     */
    private static JSONObject getBaiduChatResult(String question){
        BaiduChatParam baiduChatParam = new BaiduChatParam();
        baiduChatParam.setLog_id("001");
//        baiduChatParam.setService_id("001");
        baiduChatParam.setSession_id("001");
        BaiduChatParam.Request request = baiduChatParam.new Request();
        request.setUser_id("001");
        request.setQuery(question);
        baiduChatParam.setRequest(request);
        baiduChatParam.setSkill_ids(Arrays.asList("60734"));
        String result = Utils.httpPost(JSON.toJSONString(baiduChatParam), String.format(CHAT_URL, ACCESS_TOKEN));
        if(StringUtils.isNotBlank(result) && result.startsWith("{")){
            return JSONObject.parseObject(result);
        }
        return null;
    }
}
