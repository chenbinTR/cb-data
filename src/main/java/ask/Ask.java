package ask;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author ChenOT
 * @date 2019-09-05
 * @see
 * @since
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ask {
    private String question;
    private String answers;
    private String tag;
    private Integer id;
}
