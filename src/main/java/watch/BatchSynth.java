package watch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import utils.FileHelper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 1. 修改TARGET,该值是文件名称最后一部分（保存tts的文件命名必须为tl_word_xxxx.txt），也是文件
 * 2. 每一行都是单独的一个提示语
 *
 * @author ChenOT
 * @date 2019-08-13
 * @see
 * @since
 */
public class BatchSynth {
    public static void main(String[] args) throws IOException {
        List<String> lines = FileHelper.readFileToList("Q:\\zhiwa\\tts3.txt");
        System.out.println(lines.size());
        String text;
        String apikey;
        String mp3Url;
        Set<String> set = new HashSet<>(lines.size()+1);
        int count=0;
        for(String line:lines){
            if(++count%100 == 0) {
                System.out.println(count);
            }
            String[] items = line.split("=");
            text = items[0];
            apikey = items[1];
            if(set.contains(line)){
                System.out.println(line);
                continue;
            }
            set.add(line);

            // 请求aiwifi接口，获取音频文件链接
            mp3Url = executeAIWIFI(text, "");
            if(StringUtils.isBlank(mp3Url)){
                FileHelper.writeToTxt("Q:\\zhiwa\\apiwifi-wrong.txt", line);
                continue;
            }
            // 下载文件
            File file = new File(String.format("Q:\\zhiwa\\tts3\\%s.mp3", apikey));
            if(!FileHelper.downloadFile(mp3Url, file)){
                FileHelper.writeToTxt("Q:\\zhiwa\\url-wrong.txt", mp3Url);
            }
        }
        System.out.println(set.size());
    }

    /**
     * 调用aiwifi接口合成tts
     * @param text
     *      待合成的文本
     * @return
     *      音频文件链接
     */
    private static String executeAIWIFI(String text, String token){
        TTSParameters ttsParameters = new TTSParameters(text, token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("parameters", ttsParameters);
        try{
            String result =  FileHelper.httpPost(jsonObject.toJSONString(), "http://aiwifi.alpha.tuling123.com/speech/v2/tts");
            JSONObject jsonResult = JSON.parseObject(result);
            if(jsonResult.getInteger("code") == 200){
                return jsonResult.getJSONArray("url").getString(0);
            }else{
                System.err.println(jsonResult);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
