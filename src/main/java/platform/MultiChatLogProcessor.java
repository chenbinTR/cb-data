package platform;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import sun.rmi.runtime.Log;
import utils.UtilsMini;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 提取多轮对话
 *
 * @author ChenOT
 * @date 2020-05-25
 * @see
 * @since
 */
public class MultiChatLogProcessor {
    private static final Integer MAX_INERVAL = 5 * 60 * 1000;
    private static final List<LogEntity> LOG_ENTITY_LIST = new ArrayList<>();
    private static final Map<String, List<LogEntity>> USER_MAP = new HashMap<>();
    private static final String PATH = "Q:\\logs\\";

    public static void main(String[] args) throws IOException {
        loadLogs();
        loadUserLogs();
        loadMutilLogs();
    }

    /**
     * 计算多轮对话
     */
    private static void loadMutilLogs() {
        for(Map.Entry<String, List<LogEntity>>entry:USER_MAP.entrySet()){
//            String userid = entry.getKey();
            List<LogEntity> logEntityList = entry.getValue();
            process(logEntityList);
        }
    }
    private static void process(List<LogEntity> logEntityList){
        String startDate = "";
        List<LogEntity> tempLogEntities = new ArrayList<>();
        for(LogEntity logEntity:logEntityList){
            String endDate = logEntity.getCreateDate();
            if(StringUtils.isAnyBlank(startDate, endDate)){
                // 每一轮开始
                tempLogEntities.add(logEntity);
            }else{
                long msDiff = UtilsMini.getMsDiff(startDate, endDate);
                if(msDiff < MAX_INERVAL){
                    // 早多轮时间内
                    tempLogEntities.add(logEntity);
                }else{
                    // 超出了多轮时间
                    if(tempLogEntities.size()>2){
                        for(LogEntity logEntityTemp:tempLogEntities){
                            UtilsMini.writeToTxt(PATH+"mutil_log.txt", logEntityTemp.toString());
                        }
                        UtilsMini.writeToTxt(PATH+"mutil_log.txt", "");
                    }
                    tempLogEntities.clear();
                    startDate = "";
                    continue;
                }
            }
            startDate = logEntity.getCreateDate();
        }

        // 当前用户数据遍历完之后
        if(tempLogEntities.size()>2){
            for(LogEntity logEntityTemp:tempLogEntities){
                UtilsMini.writeToTxt(PATH+"mutil_log.txt", logEntityTemp.toString());
            }
            UtilsMini.writeToTxt(PATH+"mutil_log.txt", "");
        }
    }

    /**
     * 将日志数据整理为单个userid的数据
     */
    private static void loadUserLogs() {
        for (LogEntity logEntity : LOG_ENTITY_LIST) {
            List<LogEntity> logEntities = USER_MAP.get(logEntity.getUserid());
            if (CollectionUtils.isEmpty(logEntities)) {
                logEntities = new ArrayList<>();
            }
            logEntities.add(logEntity);
            USER_MAP.put(logEntity.getUserid(), logEntities);
        }
        LOG_ENTITY_LIST.clear();
        System.gc();
    }

    /**
     * 将日志数据整理为对象list
     */
    private static void loadLogs() {
        File file = new File(PATH + "202005.txt");
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line;
        int count = 0;
        while (it.hasNext()) {
            try {
                line = it.nextLine();
                String[] items = line.split("\t");
                String apikey = items[0];
                String question = items[1];
                String answer = items[2];
                String createDate = items[3];
                String appid = items[4];
                String parsetype = items[5];
                String userid = items[6];

                if (StringUtils.isAnyBlank(apikey, question, answer, createDate, appid, parsetype, userid)) {
                    continue;
                }
                if (!appid.startsWith("230010") || userid.equals("0")) {
                    continue;
                }
                LOG_ENTITY_LIST.add(new LogEntity(apikey, question, answer, createDate, appid, parsetype, userid));
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
