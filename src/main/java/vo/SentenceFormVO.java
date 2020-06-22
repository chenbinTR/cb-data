package vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ChenOT
 * @date 2019-05-17
 * @see
 * @since
 */
public class SentenceFormVO {
    @Getter
    @Setter
    private String sentenceType;
    @Getter @Setter private String score;
    public SentenceFormVO(String sentenceType, String score){
        this.sentenceType = sentenceType;
        this.score = score;
    }
}
