package chat.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wangsx
 * @Title: com.turing.chat.common.define
 * @Description: TODO
 * @date 2017/12/5
 */
@Data
@AllArgsConstructor
public class ParseType {
    private String parseType;
    private String module;
    private String explanation;
}
