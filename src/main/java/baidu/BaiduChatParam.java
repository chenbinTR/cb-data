package baidu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ChenOT
 * @date 2019-06-12
 * @see
 * @since
 */
public class BaiduChatParam {
    @Getter @Setter private String version= "2.0";
    @Getter @Setter private String service_id;
    @Getter @Setter private String log_id;
    @Getter @Setter private String session_id;
    @Getter @Setter private Request request;
    @Getter @Setter private List<String> skill_ids;

    class Request{
        @Getter @Setter private String user_id;
        @Getter @Setter private String query;
    }
//    private String
}
