package ask.aiyangsheng;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-12-13
 * @see
 * @since
 */
public class DataProcessor {
    public static void main(String[] args) {
        List<String> lines = Utils.readFileToList("Q:\\tiantianyangsheng.txt");
        List<String> results = new ArrayList<>();
        for(String line:lines){
            AiEntity aiEntity = JSON.parseObject(line.trim(), AiEntity.class);
            results.add(String.format("%s\t%s\t%s",aiEntity.getQuestion().replace("\t","").trim(), aiEntity.getAnswer().replace("\t","").trim(), StringUtils.join(aiEntity.getTags().toArray(), "|")));
            if(results.size() == 1000){
                Utils.writeToTxt("Q:\\ask.txt", StringUtils.join(results.toArray(), "\r\n"));
                results.clear();
            }
        }
        Utils.writeToTxt("Q:\\ask.txt", StringUtils.join(results.toArray(), "\r\n"));
    }
}
