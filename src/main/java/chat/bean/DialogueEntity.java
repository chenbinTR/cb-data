package chat.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChenOT
 * @date 2019-09-18
 * @see
 * @since
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DialogueEntity {
    @JSONField(name = "_class")
    private String class_filed = "com.turing.chat.entity.DialogueEntity";
    private int qid;
    private String question;
    private String newcmd;
    private String normalization;
    private JSONObject create_at;
    private String create_by = "贾志强";
    private String review_by = "贾志强";
    private String batch_num = "202005200101";
    private int state = 1;
    private String sensitive_word;
    private JSONObject _id;

    public DialogueEntity(int qid, String question){
        this.qid = qid;
        this.question = question;
        this.newcmd = question;
        this.normalization = question;
        create_at = new JSONObject();
        create_at.put("$date", System.currentTimeMillis());
    }

    public String getClass_filed() {
        return class_filed;
    }

    public void setClass_filed(String class_filed) {
        this.class_filed = class_filed;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getNewcmd() {
        return newcmd;
    }

    public void setNewcmd(String newcmd) {
        this.newcmd = newcmd;
    }

    public String getNormalization() {
        return normalization;
    }

    public void setNormalization(String normalization) {
        this.normalization = normalization;
    }

    public JSONObject getCreate_at() {
        return create_at;
    }

    public void setCreate_at(JSONObject create_at) {
        this.create_at = create_at;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getReview_by() {
        return review_by;
    }

    public void setReview_by(String review_by) {
        this.review_by = review_by;
    }

    public String getBatch_num() {
        return batch_num;
    }

    public void setBatch_num(String batch_num) {
        this.batch_num = batch_num;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSensitive_word() {
        return sensitive_word;
    }

    public void setSensitive_word(String sensitive_word) {
        this.sensitive_word = sensitive_word;
    }
}
