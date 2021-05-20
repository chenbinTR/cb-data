package chat.bean;

import cn.hutool.core.io.FileUtil;
import lombok.Data;
import utils.Utils;

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
        List<String> logs = FileUtil.readUtf8Lines("E:\\1.txt");
        int count = 0;
        for (String log : logs) {
            count += Integer.valueOf(log.split("\t")[2]);
        }
        for (String log : logs) {
            String[] items = log.split("\t");
            String parseType = items[0];
            int num = Integer.valueOf(items[2]);
            double bb = (double)num/(double)count*100;
            String baifenbi = String.format("%.2f", bb);
            String type = map.get(parseType).getType();
            String text = map.get(parseType).getText();
            Utils.writeToTxt("E://34.txt", parseType, baifenbi, type, text);
        }

    }
}
