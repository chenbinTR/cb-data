package book.tencentasr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2020-01-07
 * @see
 * @since
 */
public class AsrTextTask implements Runnable {
    private int count;
    public AsrTextTask(int count){
        this.count = count;
    }
    @Override
    public void run() {
        List<Integer> taskIds = new ArrayList<>();
        while (true) {
            List<String> lines = Utils.readFileToList("Q:\\book\\url_task.txt");
            if (CollectionUtils.isEmpty(lines)) {
                continue;
            }
            String result = null;
            for (String line : lines) {
                try{
                    JSONObject jsonObject = JSON.parseObject(line);
                    Integer taskId = jsonObject.getJSONObject("Data").getInteger("TaskId");
                    if(taskIds.contains(taskId)){
                        continue;
                    }
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    result = AsrTextUtils.reqAsrText(taskId);
                    String text = JSONObject.parseObject(result).getJSONObject("Data").getString("Result").replace("\\n", "").replaceAll("[0-9:.,\\[\\]]", "").replace(" ", "").trim();
                    String statusStr = JSONObject.parseObject(result).getJSONObject("Data").getString("StatusStr");
                    if("success".equals(statusStr)){
                        taskIds.add(taskId);
                        jsonObject.put("taskId", taskId);
                        jsonObject.put("text", text);
                        Utils.writeToTxt("Q:\\book\\asr_text.txt", jsonObject.toJSONString());
                    }else{
                        System.out.println(statusStr);
                    }
                }catch (Exception e){
                    System.err.println(e.getMessage());
                    System.err.println(result);
                    System.err.println(line);
                    e.printStackTrace();
                }
            }
            if (count == taskIds.size()) {
                break;
            }
        }
    }
}
