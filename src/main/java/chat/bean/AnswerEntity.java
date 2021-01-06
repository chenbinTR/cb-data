package chat.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-09-18
 * @see
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerEntity {
    @JSONField(name = "_class")
    private String class_filed = "com.turing.chat.entity.OriginalAnswer";
    private double score = 100.0;
    private int qid;
    private JSONObject create_at;
    private String create_by = "biz";
    private String review_by = "biz";
    private String batch_num = "202005200101";
    private int state = 1;
    private int aid;
    private String type = "dialogue";
    private Tag tags;
    private List<Info> infos;
    private String sensitive_word;
    private JSONObject _id;

    public AnswerEntity(int qid, String answer, int channel) {
        this.qid = qid;
        this.aid = answer.hashCode() + RandomUtils.nextInt(1, 10000);
        this.tags = new Tag(channel);
        Info info = new Info(answer);
        infos = new ArrayList<>(1);
        infos.add(info);
        create_at = new JSONObject();
        create_at.put("$date", System.currentTimeMillis());
    }

    public String getClass_filed() {
        return class_filed;
    }

    public void setClass_filed(String class_filed) {
        this.class_filed = class_filed;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
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

    public long getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Tag getTags() {
        return tags;
    }

    public void setTags(Tag tags) {
        this.tags = tags;
    }

    public List<Info> getInfos() {
        return infos;
    }

    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }

    public class Info {
        private int type = 0;
        private String content;

        public Info(String content) {
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public class Tag {
        /**
         *  channel:0  通用语料
         *  channel:1  语料只适用于儿童，优先选取
         *  channel:-1 语料不适用于儿童
         */
        private int channel;
        private String sentence_form_score = "0.9997557401657104";
        private String sentence_form = "陈述句";

        public Tag(int channel) {
            this.channel = channel;
        }

        public String getSentence_form_score() {
            return sentence_form_score;
        }

        public void setSentence_form_score(String sentence_form_score) {
            this.sentence_form_score = sentence_form_score;
        }

        public String getSentence_form() {
            return sentence_form;
        }

        public void setSentence_form(String sentence_form) {
            this.sentence_form = sentence_form;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }
    }


}
