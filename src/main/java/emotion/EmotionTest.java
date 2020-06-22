//package emotion;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.turing.utils.TuringNlpUtils;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.LineIterator;
//import utils.Utils;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * @author ChenOT
// * @date 2019-06-19
// * @see
// * @since
// */
//public class EmotionTest {
//    public static void main(String[] args) {
//        String req = "{\"globalId\": \"5d92b7f49d8947668e65f23d6e44e197\",\"texts\": [\"%s\"]}";
//
//        String url = "http://192.168.10.32:23337/nlpemotion/emotion2_5_json";
//        String url2 = "http://192.168.10.32:23334/nlpemotion/emotion_json";
//
//        File file = new File("Q:\\12.txt");
//        LineIterator it = null;
//        try {
//            it = FileUtils.lineIterator(file, "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int count = 0;
//        while (it.hasNext()) {
////            System.out.println(++count);
//            String line = it.nextLine();
////            if(line.indexOf("哈")>-1){
////                continue;
////            }
////            String se = TuringNlpUtils.originalSegment(line);
//            try {
////                Thread.sleep(100);
//                String reqStr = String.format(req, line);
//                int codeNew = getEmotionCode(url, reqStr);
//                int codeOld = getEmotionCode(url2, reqStr);
//
////                float probability = jsonObject.getFloat("probability");
////                if(code == 10116|| code == 10118 || code ==10123 ){
//                    Utils.writeToTxt("Q:\\13.txt", String.format("%s\t%s\t%d\t%d",line.replaceAll("/[a-z]+ *",""), line,codeOld, codeNew));
////                }
//            } catch (Exception e) {
//                System.out.println(line);
////                e.printStackTrace();
////                Utils.writeToTxt("Q:\\13.txt", String.format("%s\t%s\t%d",line.replaceAll("/[a-z]+ *",""), line, 0));
//            }
//        }
//        LineIterator.closeQuietly(it);
//    }
//
//    private static int getEmotionCode(String url, String data){
//        String result = Utils.httpPost(data, url);
//        JSONArray emotions = JSONObject.parseObject(result).getJSONObject("data").getJSONArray("results").getJSONObject(0).getJSONArray("emotions");
//        if(emotions.size() == 0){
//            return 0;
//        }
//        JSONObject jsonObject = emotions.getJSONObject(0);
//        int code = jsonObject.getInteger("code");
//        return code;
//    }
//}
