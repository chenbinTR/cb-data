package turingshield;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author ChenOT
 * @date 2019-06-20
 * @see
 * @since
 */
public class ProcessLog {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        int length = "2019-12-05 23:59:59".length();
        String[] files = {"C:\\Users\\cb\\Downloads\\warn2\\warn\\shield.warn.log.2019-12-051","C:\\Users\\cb\\Downloads\\warn2\\warn\\shield.warn.log.2019-12-05"};
        for(String fileName:files){
            File filePath = new File(fileName);
            LineIterator lineIterator = null;
            try {
                lineIterator = FileUtils.lineIterator(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(lineIterator.hasNext()){
                String time = lineIterator.nextLine().substring(0, length);
                Integer value = map.getOrDefault(time, 1);
                value = value+1;
                map.put(time, value);
                System.out.println(time);
            }
            System.out.println(map.size());
//            Comparator<Integer> comparator = (Integer s1, Integer s2) -> (s1>s2?1:-1);
//            MapUtil.sort(map, comparator);
        }
        for(String key:map.keySet()){
            Utils.writeToTxt("Q:\\11.txt", key+"\t"+map.get(key));
        }
//        Set<String> resultSet = new HashSet<>();
//        for (String fileName : files) {
//            System.out.println(fileName);
//            List<String> stringList = Utils.readFileToList("C:\\Users\\cb\\Downloads\\turingshile_log_07\\"+fileName);
//            JSONObject jsonObject;
//            for (String item : stringList) {
//                try {
//                    int start = item.indexOf("shield no_pass:") + "shield no_pass:".length();
//                    item = item.substring(start);
//                    jsonObject = JSONObject.parseObject(item);
//                    JSONArray ja = jsonObject.getJSONArray("datas");
//                    for (int i = 0; i < ja.size(); i++) {
//                        JSONObject jsonItem = ja.getJSONObject(i);
//                        int sensitiveInfo = jsonItem.getJSONObject("sensitiveInfo").getInteger("level");
//                        if (sensitiveInfo > 0 && sensitiveInfo != 10) {
//                            resultSet.add(jsonItem.getString("precossCommend")+"\t"+sensitiveInfo);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        Utils.writeToTxt("Q:\\turingshile_log.txt", StringUtils.join(resultSet.toArray(), "\r\n"));
    }
}
