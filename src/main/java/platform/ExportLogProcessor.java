package platform;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import utils.CbMysqlClientUtils;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * @author ChenOT
 * @date 2020-06-03
 * @see
 * @since
 */
public class ExportLogProcessor {
    private static List<String> apikeys = Arrays.asList("ceaa80b7323a443791eae62be7e5db2d");

    public static void main(String[] args) {
        for (String apikey : apikeys) {
//            getCommonQuestion("");
//            System.out.println(apikey);
            getLog(apikey);
        }
    }

    private static void getLog(String apiKey) {
        LineIterator lineIterator = null;
        try {
            lineIterator = FileUtils.lineIterator(new File("C:\\Users\\cb\\Downloads\\" + apiKey + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count = 0;
        while (lineIterator.hasNext()) {
            try {
                String line = lineIterator.nextLine();
                String[] items = line.split("\t");
                if (items.length != 8) {
                    continue;
                }
                Utils.writeToTxt("Q:\\logs\\"+apiKey+".txt", line);
                count++;
//                if(count>=100000){
//                    break;
//                }
//                if(StringUtils.isAnyBlank(question,appid,parsetype,answer)){
//                    continue;
//                }
//                if(!appid.equals("2300101") && !appid.equals("2300102")){
//                    continue;
//                }
//                if(parsetype.equals("50")){
//                    System.out.println(line);
//                    Utils.writeToTxt("Q:\\logs\\1.txt", line);
//                }
//                    Utils.writeToTxt("Q:\\logs\\1.txt", line);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }

    private static void getCommonQuestion(String apikey) {
        Connection connection = null;
        try {
            connection = CbMysqlClientUtils.createConnection(CbMysqlClientUtils.ConnectType.PROD_LOG);
            if (null == connection || connection.isClosed()) {
                System.out.println("数据库连接失败");
                return;
            }
            Statement statement = connection.createStatement();
            String sql = "select apikey,question,answer,appkey,appid,parsetype,create_date,userid from platform_cloud_log_202006 where id<10000000 and (version='2.0' or version='p1.0') and (appid=1100101 or appid=1100102)";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("question"));
                Utils.writeToTxt("Q:\\logs\\platform_wiki.txt", rs.getString("question").trim()+"\t"+rs.getString("answer").trim());
//                System.out.println(JSONObject.toJSONString(rs));
            }
            rs.close();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("完成！");
    }
}
