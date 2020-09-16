package log2;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.LogContent;
import com.aliyun.openservices.log.common.LogGroupData;
import com.aliyun.openservices.log.common.QueriedLog;
import com.aliyun.openservices.log.exception.LogException;
import com.aliyun.openservices.log.request.GetLogsRequest;
import com.aliyun.openservices.log.response.BatchGetLogResponse;
import com.aliyun.openservices.log.response.GetCursorResponse;
import com.aliyun.openservices.log.response.GetLogsResponse;
import utils.SymmetricEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @User: fengxinxin
 * @Date: 2020-07-08
 * @Time: 15:53
 * @copyright uzoo
 */
public class Log2Service {

    private final static String logStore = "user-operation-log";
    private final static String project = "edu-userlog";
    private final static Client client;

    static {
        String endpoint = SymmetricEncoder.AESDncode("AYhz29YShmLYuqf7dTirRJRXnnU6YKKqo4i5j/buN7k=");
        String accessKeyId = SymmetricEncoder.AESDncode("yXNdHeNl3KVgSKaVKCX721etgIyweG48qW3D658GA2w=");
        String acccessKeySecret = SymmetricEncoder.AESDncode("ILam7bdyc20CO5mUNY1cQmtC3pogusp1bLuWN+qjv2E=");
        client = new Client(endpoint, accessKeyId, acccessKeySecret);
    }

    public static void main(String[] args) {
        System.out.println("1");
        findLog2ParamsByGlobalId("");
//        readeData();
    }

    private static void findLog2ParamsByGlobalId(String globalId) {
        try {
            String query = "code: 1056";
            int from = (int) (System.currentTimeMillis() / 1000 - 30 * 24 * 60 * 60);
            int to = (int) (System.currentTimeMillis() / 1000);
            final int line = 50;
            int index = 0;

            // IsCompleted()返回true，表示查询结果是准确的，如果返回false，则重复查询
            int i = 0;
            while (i < 1) {
                ++i;
                GetLogsRequest req = new GetLogsRequest(project,
                        logStore, from, to, null, query, index*line, line, false);
                GetLogsResponse res = client.GetLogs(req);
                System.out.println("res:" + JSONObject.toJSONString(res));
                if (res == null || !res.IsCompleted()) {
                    System.err.println("异常");
                    return;
                }
                ArrayList<QueriedLog> queriedLogs = res.GetLogs();
                if (queriedLogs == null || queriedLogs.isEmpty()) {
                    System.err.println("异常");
                    return;
//                    QueriedLog queriedLog = queriedLogs.get(0);
//                    ArrayList<LogContent> logContents = queriedLog.GetLogItem().mContents;
//                    for (LogContent logContent : logContents) {
//                        System.out.println(logContent.GetKey() + ": " + logContent.GetValue());
//                    }
                }
                for (QueriedLog queriedLog : queriedLogs) {
                    ArrayList<LogContent> logContents = queriedLog.GetLogItem().mContents;
                    System.out.println(JSONObject.toJSONString(logContents));
//                      for (LogContent logContent : logContents) {
//                        System.out.println(logContent.GetKey() + ": " + logContent.GetValue());
//                            System.out.println(JSONObject.toJSONString(logContent));
//                    }
//                        }
                }
                ++index;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readeData() {
        int shardId = 0;
        GetCursorResponse res = null;
        try {
            // 获取最近1个小时接收到的第一批日志的cursor位置
            long fromTime = (int) (System.currentTimeMillis() / 1000.0 - 3600);
            res = client.GetCursor(project, logStore, shardId, fromTime);
            System.out.println("shard_id:" + shardId + " Cursor:" + res.GetCursor());
        } catch (LogException e) {
            e.printStackTrace();
        }

        String cursor = res.GetCursor();
        while (true) {
            BatchGetLogResponse logDataRes = null;
            try {
                logDataRes = client.BatchGetLog(
                        project, logStore, shardId, 100, cursor);
            } catch (LogException e) {
                e.printStackTrace();
            }
            // 读取到的数据
            try {
                List<LogGroupData> logGroups = logDataRes.GetLogGroups();
                for (LogGroupData logGroup : logGroups) {
                    System.out.println(logGroup.GetLogGroup());
                }
            } catch (LogException e) {
                e.printStackTrace();
            }

            String nextCursor = logDataRes.GetNextCursor();  // 下次读取的位置
            System.out.print("The Next cursor:" + nextCursor);
            if (cursor.equals(nextCursor)) {
                break;
            }
            cursor = nextCursor;
        }

    }
}
