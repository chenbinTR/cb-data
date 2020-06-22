package ask.sogou;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author ChenOT
 * @date 2019-11-28
 * @see
 * @since
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SogoUrl {
    private String url;
    private List<String> tags;
    private List<String> infos;
}
