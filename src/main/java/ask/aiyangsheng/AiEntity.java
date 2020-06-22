package ask.aiyangsheng;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ChenOT
 * @date 2019-11-29
 * @see
 * @since
 */
@Setter
@Getter
@AllArgsConstructor
public class AiEntity {
    private String answer;
    private String question;
    private List<String> tags;
}
