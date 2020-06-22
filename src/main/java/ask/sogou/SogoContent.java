package ask.sogou;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ChenOT
 * @date 2019-11-28
 * @see
 * @since
 */
@Getter
@Setter
public class SogoContent extends SogoUrl {
    private String title;
    private List<AnswerInfo> answerInfos;

    @AllArgsConstructor
    @Getter
    @Setter
    public class AnswerInfo{
        private String content;
        private String like;
        private String time;
        private String toprgt;
    }
}
