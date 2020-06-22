package book.tencentasr;

import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.asr.v20190614.models.CreateRecTaskRequest;
import com.tencentcloudapi.asr.v20190614.models.CreateRecTaskResponse;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import utils.Utils;
import java.io.IOException;
import java.util.List;

public class CreateRecTask {
    public static void main(String[] args) throws IOException {
        List<String> lines = Utils.readFileToList("Q:\\book\\url.txt");
        new Thread(new AsrTextTask(lines.size())).start();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("EngineModelType", "16k_0");
        jsonObject.put("ResTextFormat", 0);
        jsonObject.put("SourceType", 0);
        jsonObject.put("ChannelNum", 1);
        for(String url:lines){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //采用音频 URL 方式
            try {
                jsonObject.put("Url", url);
                CreateRecTaskRequest req = CreateRecTaskRequest.fromJsonString(jsonObject.toJSONString(), CreateRecTaskRequest.class);
                CreateRecTaskResponse resp = TencentClient.getClientInstance().CreateRecTask(req);
                JSONObject jsonObject1 = JSONObject.parseObject(CreateRecTaskRequest.toJsonString(resp));
                jsonObject1.put("url",url);
                Utils.writeToTxt("Q:\\book\\url_task.txt", jsonObject1.toJSONString());
            } catch (TencentCloudSDKException e) {
                Utils.writeToTxt("Q:\\book\\url_task_error.txt", url);
                System.err.println(e.toString());
            }
        }
    }
}