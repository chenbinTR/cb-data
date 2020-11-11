package ask;

import com.alibaba.fastjson.JSON;
import utils.UtilsMini;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ChenOT
 * @date 2019-12-02
 * @see
 * @since
 */
public class Process {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        int count = 0;
        List<String> qaList = UtilsMini.readFileToList("C:\\Users\\cb\\Downloads\\nlp_qa_platform_helth.txt");
        for(String line:qaList){
            Ask ask = JSON.parseObject(line, Ask.class);
            String tag = ask.getTag();
            String question = ask.getQuestion();
            String answer = ask.getAnswers();
            if(tag.contains("两性")){
                continue;
            }
            UtilsMini.writeToTxt("Q:\\ask\\qa_data.txt", String.format("%s\t%s\t%s", question, answer, tag));
            set.add(tag);
        }
        set.stream().forEach(item -> System.out.println(item));
        System.out.println(count);
    }
}
