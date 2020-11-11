package ask;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import org.apache.commons.lang3.StringUtils;
import utils.CbMysqlClientUtils;
import utils.UtilsMini;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ChenOT
 * @date 2019-11-28
 * @see
 * @since
 */
public class ExprotNlpqa {
    private static String SQL = "SELECT id,question,answer,category FROM %s where id>%d limit 10000";
    private static String tables = "0123456789abcdef";
    private static String path = "/home/appuser/chenbin/nlp-qa-platform/";
    private static String[] HELTH_KEY_WORD = {"健康","医疗","养生"};

    public static void main(String[] args) {
        char[] ts = tables.toCharArray();
        for (char t : ts) {
            String tableName = "qa_cache_knowledge_hanlp_" + t;
            System.out.println(tableName);
            selectTable(tableName);
        }
    }

    private static void selectTable(String table) {
        Connection connection = null;
        int startId = 0;
        while (startId > -1) {
            int finalId = 0;
            try {
                connection = CbMysqlClientUtils.createConnection(CbMysqlClientUtils.ConnectType.DEV_QA_PLATFORM);
                if (null == connection || connection.isClosed()) {
                    System.out.println("数据库连接失败");
                    return;
                }
                String sql = String.format(SQL, table, startId);
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                boolean flag = false;
                while (rs.next()) {
                    flag = true;
                    String question = filterString(rs.getString("question"));
                    String answer = filterString(rs.getString("answer"));
                    String tag = rs.getString("category");
                    finalId = rs.getInt("id");
                    Ask ask = new Ask(question, answer, tag, finalId);
                    filterHelth(ask);
                    UtilsMini.writeToTxt(path + table + ".txt", JSONObject.toJSONString(ask));
                }
                if(!flag){
                    finalId = -1;
                }
                startId = finalId;
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
        }
    }

    private static String filterString(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        str = str.trim().replace(" ", "").replace("\t", "").replace("\n", "").replace("\r", "");
        return str;
    }

    private static void filterHelth(final Ask ask){
        final String tag = ask.getTag();
        if(StringUtils.isBlank(tag)){
            return;
        }
        if(UtilsMini.isContains(tag, HELTH_KEY_WORD)){
            UtilsMini.writeToTxt(path + "nlp_qa_platform_helth.txt", JSONObject.toJSONString(ask));
        }
    }
}
