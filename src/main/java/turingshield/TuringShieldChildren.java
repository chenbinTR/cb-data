package turingshield;

import com.alibaba.fastjson.JSONObject;
import utils.Utils;
import java.io.IOException;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-06-19
 * @see
 * @since
 */
public class TuringShieldChildren {
    private final static String PATH = "Q:\\Colleague\\张清\\图灵盾\\";
    private final static String TEST_FILE = PATH + "百科数据原始.txt";
    private final static String RESULT_FILE = PATH + "百科数据.txt";

    public static void main(String[] args) throws IOException {
        List<String> datas = Utils.readFileToList(TEST_FILE);
        System.out.println(datas.size());
        int count = 0;
        for (String excelEntity : datas) {
            ++count;
            if(count % 1000 == 0) {
                System.out.println(count);
            }
            String id = excelEntity.split("\t")[0];
            String simi = excelEntity.split("\t")[1];
            try {
                String result = TuringShieldUtil.requestTuringShield(TuringShieldConfig.DEV_URL_v3, simi);
                JSONObject jsonObject = JSONObject.parseObject(result).getJSONArray("datas").getJSONObject(0);
                int sensitiveInfo = jsonObject.getJSONObject("sensitiveInfo").getInteger("level");
                Utils.writeToTxt(RESULT_FILE, String.format("%s\t%s\t%d", id, simi, sensitiveInfo));
            } catch (Exception e) {
                e.printStackTrace();
                Utils.writeToTxt(RESULT_FILE, String.format("%s\t%s\t%d", id, simi, 10));
            }
        }
    }
}
