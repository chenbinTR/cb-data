//package book.wotewode;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.iflytek.msp.cpdb.lfasr.exception.LfasrException;
//import com.iflytek.msp.cpdb.lfasr.model.LfasrType;
//import com.iflytek.msp.cpdb.lfasr.model.Message;
//import utils.Utils;
//
//import java.util.HashMap;
//import java.util.List;
//
///**
// * @author ChenOT
// * @date 2020-01-08
// * @see
// * @since
// */
//public class XunfeiAsr {
//    private static final LfasrType type = LfasrType.LFASR_STANDARD_RECORDED_AUDIO;
//    private static final String END_TADK_ID = "0-1";
//    /**
//     * 调用识别，获取task_id
//     * @param filePath
//     * @return
//     */
//    private static String getTaskId(String filePath){
//        // 获取上传任务ID
//        String task_id = "";
//        HashMap<String, String> params = new HashMap<>();
//        params.put("has_participle", "true");
//        try {
//            // 上传音频文件
//            Message uploadMsg = XunfeiAsrClient.getClient().lfasrUpload(filePath, type, params);
//
//            // 判断返回值
//            int ok = uploadMsg.getOk();
//            if (ok == 0) {
//                // 创建任务成功
//                task_id = uploadMsg.getData();
//            } else {
//                // 创建任务失败-服务端异常
//                System.out.println("ecode=" + uploadMsg.getErr_no());
//                System.out.println("failed=" + uploadMsg.getFailed());
//            }
//        } catch (LfasrException e) {
//            // 上传异常，解析异常描述信息
//            Message uploadMsg = JSON.parseObject(e.getMessage(), Message.class);
//            System.out.println("ecode=" + uploadMsg.getErr_no());
//            System.out.println("failed=" + uploadMsg.getFailed());
//            if(uploadMsg.getFailed().contains("预处理服务时长不足")){
//                return END_TADK_ID;
//            }
//        }
//        return task_id;
//    }
//    public static void main(String[] args) {
//        List<String> infos = Utils.readFileToList("E:\\wotewode\\info.txt");
//        new Thread(new XunfeiAsrTextTask(infos.size())).start();
//        JSONObject jsonObject;
//        String mp3Name;
//        String taskId;
//        for(String info:infos){
//            try {
//                Thread.sleep(1*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            jsonObject = JSON.parseObject(info);
//            mp3Name = jsonObject.getString("mp3_name");
//            taskId = getTaskId(String.format("E:\\wotewode\\mp3\\%s",mp3Name));
//            if(END_TADK_ID.equals(taskId)){
//                break;
//            }
//            jsonObject.put("task_id", taskId);
//            Utils.writeToTxt("E:\\wotewode\\task_id.txt", jsonObject.toJSONString());
//        }
//    }
//}
