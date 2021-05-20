package chat.bean;

import cn.hutool.core.io.FileUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangsx
 * @Title: com.turing.chat.common.define
 * @Description: TODO
 * @date 2017/12/5
 */
@Data
public class ParseType {
    private String parseType;
    private String type;
    private String text;


    public static void main(String[] args) {
        List<String> lines = FileUtil.readUtf8Lines(ParseType.class.getResource("/").getPath()+"appid.txt");
        Map<String, ParseType> map = new HashMap<>();
        for (String line : lines) {
            String[] items = line.split("\t");
            ParseType parseType = new ParseType();
            parseType.setParseType(items[0]);
            parseType.setType(items[1]);
            parseType.setText(items[2]);
            map.put(parseType.getParseType(), parseType);
        }
        System.out.println(map);



    }
}
