package book.wotewode;

import com.alibaba.fastjson.JSONObject;
import utils.Utils;
import utils.FileHelper;

import java.io.File;
import java.util.List;

/**
 * @author wangsx
 * @Title: book.wotewode.DownloadMp3Processor
 * @Description: TODO
 * @date 2020/1/8
 */
public class DownloadMp3Processor {
    public static void main(String[] args) {
        List<String> urls = Utils.readFileToList("E:\\wotewode\\urls.txt");
        int count = 0;
        for(String url:urls){
            String name = url.substring(url.lastIndexOf("/")+1);
            try{
                File file = new File(String.format("E:\\wotewode\\mp3\\%s", name));
                if(!FileHelper.downloadFile(url, file)){
                    Utils.writeToTxt("E:\\wotewode\\mp3_download_error_url.txt", url);
                }
            }catch (Exception e){
                e.printStackTrace();
                Utils.writeToTxt("E:\\wotewode\\mp3_download_error_url.txt", url);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url",url);
            jsonObject.put("mp3_name", name);
            Utils.writeToTxt("E:\\wotewode\\info.txt", jsonObject.toJSONString());
            System.out.println(++count);
        }
    }
}
