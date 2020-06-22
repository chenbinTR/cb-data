//package turingshield;
//
//import com.alibaba.fastjson.JSONObject;
//import com.turing.utils.TuringStringUtils;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.LineIterator;
//import utils.Utils;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
///**
// * @author ChenOT
// * @date 2019-06-19
// * @see
// * @since
// */
//public class TuringShieldTest {
//    private final static String PATH = "Q:\\Colleague\\张清\\图灵盾\\";
//    private final static String TEST_FILE = PATH + "turingshile_log.txt";
//    private final static String RESULT_FILE = PATH + "turingshile_log_20190826_result_v3_dev.txt";
//    public static void main(String[] args) throws IOException {
//        File file = new File(TEST_FILE);
//        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
//        while (it.hasNext()){
//            String simi = it.nextLine().trim();
//            try {
////                simi = JSONObject.parseObject(simi).getString("question");
//                String result = TuringShieldUtil.requestTuringShield(TuringShieldConfig.DEV_URL_v3, simi);
//                JSONObject jsonObject = JSONObject.parseObject(result).getJSONArray("datas").getJSONObject(0);
//                int violenceInfo = jsonObject.getJSONObject("violenceInfo").getInteger("level");
//                int pornoInfo = jsonObject.getJSONObject("pornoInfo").getInteger("level");
//                int sensitiveInfo = jsonObject.getJSONObject("sensitiveInfo").getInteger("level");
//                if(isFilered(violenceInfo) || isFilered(pornoInfo) ||isFilered(sensitiveInfo)){
//                    Utils.writeToTxt(RESULT_FILE, String.format("%s\t%d\t%d\t%d", simi, violenceInfo, pornoInfo, sensitiveInfo));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private static boolean isFilered(int value){
//        if(value > 0 && value != 10){
//            return true;
//        }
//        return false;
//    }
//}
