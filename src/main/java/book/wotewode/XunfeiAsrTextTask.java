package book.wotewode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.msp.cpdb.lfasr.exception.LfasrException;
import com.iflytek.msp.cpdb.lfasr.model.Message;
import com.iflytek.msp.cpdb.lfasr.model.ProgressStatus;
import org.apache.commons.collections.CollectionUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ChenOT
 * @date 2020-01-07
 * @see
 * @since
 */
public class XunfeiAsrTextTask implements Runnable {
    private int count;

    public XunfeiAsrTextTask(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        Set<String> fileNames = Utils.readFileToSet("E:\\wotewode\\file_success_name.txt");
        while (true) {
            List<String> lines = Utils.readFileToList("E:\\wotewode\\task_id.txt");
            if (CollectionUtils.isEmpty(lines)) {
                continue;
            }
            for (String line : lines) {
                JSONObject jsonObject = JSON.parseObject(line);
                String fileName = jsonObject.getString("mp3_name");
                String taskId = jsonObject.getString("task_id");
                if (fileNames.contains(fileName)) {
                    continue;
                }
                try {
                    Thread.sleep(1*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 获取处理进度
                Message progressMsg;
                try {
                    progressMsg = XunfeiAsrClient.getClient().lfasrGetProgress(taskId);
                } catch (LfasrException e) {
                    e.printStackTrace();
                    progressMsg = null;
                }
                // 如果返回状态不等于0，则任务失败
                if (null == progressMsg || progressMsg.getOk() != 0) {
                    System.err.println("任务失败ecode=" + progressMsg.getErr_no());
                    System.err.println("任务失败failed=" + progressMsg.getFailed());
                    fileNames.add(fileName);
                    Utils.writeToTxt("E:\\wotewode\\task_id_err.txt", line);
                    continue;
                }
                ProgressStatus progressStatus = JSON.parseObject(progressMsg.getData(), ProgressStatus.class);
                if (progressStatus.getStatus() == 9) {
                    // 处理完成
                    System.out.println("处理完成. task_id:" + taskId);
                    Message resultMsg;
                    try {
                        resultMsg = XunfeiAsrClient.getClient().lfasrGetResult(taskId);
                    } catch (LfasrException e) {
                        e.printStackTrace();
                        resultMsg = null;
                    }
                    // 如果返回状态等于0，则获取任务结果成功
                    if (null != resultMsg && resultMsg.getOk() == 0) {
                        jsonObject.put("asr_result", JSONObject.parseArray(resultMsg.getData()));
                        Utils.writeToTxt("E:\\wotewode\\task_id_success.txt", jsonObject.toJSONString());
                        Utils.writeToTxt("E:\\wotewode\\file_success_name.txt", fileName);
                    } else {
                        // 获取任务结果失败
                        System.err.println("获取任务结果失败ecode=" + resultMsg.getErr_no());
                        System.err.println("获取任务结果失败failed=" + resultMsg.getFailed());
                        Utils.writeToTxt("E:\\wotewode\\task_id_err.txt", line);
                    }
                    fileNames.add(fileName);
                } else {
                    // 未处理完成
                    System.out.println("未处理完成task_id:" + taskId + ", status:" + progressStatus.getDesc());
                }
            }
        }
    }
}
