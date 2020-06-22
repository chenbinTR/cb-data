package ask.sogou;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;
import utils.UtilsMini;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-12-02
 * @see
 * @since
 */
public class ResultFilteProcessor {
    public static void main(String[] args) {
        extractResult();
    }
    private static void extractResult(){
        int count = 0;
        List<String> list = UtilsMini.readFileToList("Q:\\ask\\sogo_medical_url_content.txt");
        List<String> results = new ArrayList<>();
        for(String line:list){
            System.out.println(++count);
            SogoContent sogoContent = JSON.parseObject(line, SogoContent.class);
            String tag = StringUtils.join(sogoContent.getTags().toArray(), "|");
            String question = sogoContent.getTitle().trim();
            List<SogoContent.AnswerInfo> answerInfos = sogoContent.getAnswerInfos();

            String answer = null;
            for(SogoContent.AnswerInfo answerInfo:answerInfos){
                if(answerInfo.getToprgt().contains("采纳")){
                    answer = answerInfo.getContent().trim();
                    break;
                }
            }
            if(StringUtils.isBlank(answer)){
                answer = sogoContent.getAnswerInfos().get(0).getContent().trim();
            }
            String result = String.format("%s\t%s\t%s", Utils.clenaString(question), Utils.clenaString(answer), Utils.clenaString(tag) );
            results.add(result);
            if(result.length() == 10000){
                UtilsMini.writeToTxt("Q:\\ask\\sogo_medical_data.txt", StringUtils.join(results.toArray(), "\r\n"));
                results.clear();
            }
        }
        UtilsMini.writeToTxt("Q:\\ask\\sogo_medical_data.txt", StringUtils.join(results.toArray(), "\r\n"));
    }
}
