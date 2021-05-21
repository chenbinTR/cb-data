package chat;

import chat.bean.ParseType;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.Resource;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @Date 2021/5/21
 */
public class ParseTypeProcessor {
    private static final List<ParseType> parseTypeList = new ArrayList<>(100);

    static {
        try {
            List<String> lines = readResourcesFileLine("appid.txt");
            for (String line : lines) {
                String[] items = line.split("\t");
                parseTypeList.add(new ParseType(items[0], items[1], items[2]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        parseTypeList.forEach(System.out::println);
    }

    /**
     * 读取resources下文件
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static List<String> readResourcesFileLine(String fileName) throws Exception {
        //  ClassPathResource classPathResource = new ClassPathResource("resource.properties");
        List<String> results = new ArrayList<>();
        Resource resource = new ClassPathResource(fileName);
        InputStream is = resource.getStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String data = null;
        while ((data = br.readLine()) != null) {
            results.add(StringUtils.strip(data));
        }
        br.close();
        isr.close();
        is.close();
        return results;
    }
}
