package turingshield;

import com.alibaba.fastjson.JSONObject;
import utils.Utils;

import java.util.List;

/**
 * @author ChenOT
 * @date 2019-06-19
 * @see
 * @since
 */
public class TuringShieldFaq {
    private final static String PATH = "C:\\Users\\cb\\Downloads\\";
    private final static String TEST_FILE = PATH + "minganshuju.txt";
    private final static String RESULT_FILE = PATH + "faq_result_v3_dev.txt";

    public static void main(String[] args) {
        List<String> stringList = Utils.readFileToList(TEST_FILE);
        for (String simi : stringList) {
            try {
                if(!simi.contains("\t")){
                    continue;
                }
                String[] items = simi.split("\t");
                if(items.length !=2 ){
                    continue;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for(String item:items){
                    stringBuilder.append(item);
                    int v = 0;
                    int p = 0;
                    int s = 0;
                    try{
                        String result = TuringShieldUtil.requestTuringShield(TuringShieldConfig.DEV_URL_v3, simi);
                        JSONObject jsonObject = JSONObject.parseObject(result).getJSONArray("datas").getJSONObject(0);
                        v = jsonObject.getJSONObject("violenceInfo").getInteger("level");
                        p = jsonObject.getJSONObject("pornoInfo").getInteger("level");
                        s = jsonObject.getJSONObject("sensitiveInfo").getInteger("level");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    stringBuilder.append("\t");
                    stringBuilder.append(String.format("%d-%d-%d", v,p,s));
                    stringBuilder.append("\t");
                }
                Utils.writeToTxt(RESULT_FILE, stringBuilder.toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(simi);
//                Utils.writeToTxt(RESULT_FILE, String.format("%s\t%d\t%d\t%d", simi, 0, 0, 0));
            }
        }
    }
}
