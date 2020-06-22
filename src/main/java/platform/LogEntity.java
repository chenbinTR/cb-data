package platform;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ChenOT
 * @date 2020-05-25
 * @see
 * @since
 */
@Data
@AllArgsConstructor
public class LogEntity {
    private String apikey;
    private String question;
    private String answer;
    private String createDate;
    private String appid;
    private String parseType;
    private String userid;

    @Override
    public String toString(){
        return this.apikey+"\t"+this.userid+"\t"+this.question+"\t"+this.answer+"\t"+this.createDate+"\t"+this.appid+"\t"+this.parseType;
    }
}
