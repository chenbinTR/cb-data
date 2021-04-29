package generics;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author ChenOT
 * @Date 2021/4/27
 */
@Data
public class Test {
    private String text;
    private String code;

    public static <T> T parseJsonToObj(String json, Class<T> c) {
        try {
            return JSONObject.parseObject(json, c);
        } catch (Exception e) {
        }
        return null;
    }
}
