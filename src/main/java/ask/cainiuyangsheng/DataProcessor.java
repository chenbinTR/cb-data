package ask.cainiuyangsheng;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;
import utils.UtilsMini;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-11-29
 * @see
 * @since
 */
public class DataProcessor {
    public static void main(String[] args) {
        List<String> cainiuDataList = UtilsMini.readFileToList("Q:\\ask\\cainiu.txt");
        List<String> results = new ArrayList<>();
        Data data;
        String question;
        List<String> contents;
        String position;
        for(String line:cainiuDataList){
            if(line.contains("关键字搜索")){
                continue;
            }
            data = JSON.parseObject(line, Data.class);
            contents = data.getContents();
            String answer = processContents(contents);

            if(StringUtils.isBlank(answer)){
                continue;
            }

            position = data.getPosition();
            position = Utils.clenaString(position
                    .replace("当前位置","")
                    .replace("：","")
                    .replace("正文","")
                    .replace(">","|"));

            question = Utils.clenaString(data.getTitle());
            if(StringUtils.isBlank(question)){
                continue;
            }
            results.add(String.format("%s\t%s\t%s", question, answer, position));
        }

        Utils.writeToTxt("Q:\\ask\\cainiuyangsheng_data.txt", StringUtils.join(results.toArray(),"\r\n"));
    }
    private static String processContents(List<String> contents){
        StringBuilder sb = new StringBuilder();
        for(String item:contents){
            String line = Utils.clenaString(item);
            if(StringUtils.isBlank(line)){
                continue;
            }
            sb.append(line);
        }
        return sb.toString();
    }
}
