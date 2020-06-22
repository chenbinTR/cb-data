package chat;

import chat.bean.AnswerEntity;
import chat.bean.DialogueEntity;
import com.alibaba.fastjson.JSON;
import utils.Utils;

import java.util.List;

/**
 * @author ChenOT
 * @date 2020-01-03
 * @see
 * @since
 */
public class DataProcessor {
    public static void main(String[] args) {
//        List<String> dialogs = Utils.readFileToList("C:\\Users\\cb\\Downloads\\twmaze\\dialogue_sensitive_prod_20200108.txt");
        List<String> answers = Utils.readFileToList("E:\\chat\\answer_sensitive_20200121.txt");

//        for (String dialogue : dialogs) {
//            DialogueEntity dialogueEntity = JSON.parseObject(dialogue, DialogueEntity.class);
//            Utils.writeToTxt("C:\\Users\\cb\\Downloads\\twmaze\\dialogue_sensitive_20200108.txt",
//                    dialogueEntity.get_id().getString("$oid") + "\t" +
//                            dialogueEntity.getQid() + "\t"
//                            + dialogueEntity.getQuestion() + "\t"
//                            + dialogueEntity.getSensitive_word()
//            );
//        }

        for (String answer : answers) {
            AnswerEntity answerEntity = JSON.parseObject(answer, AnswerEntity.class);
            String content = answerEntity.getInfos().get(0).getContent();
            Utils.writeToTxt("E:\\chat\\answer_sensitive_20200121——1.txt",
                    answerEntity.get_id().getString("$oid") + "\t" +
                            answerEntity.getType()+"\t"+
                            answerEntity.getQid() + "\t"
                            + content + "\t"
                            + answerEntity.getSensitive_word()
            );
        }

    }
}
