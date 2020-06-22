package platform;

import com.mysql.jdbc.Connection;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author cb
 * @date 2018-10-23 10:01
 */
public class Test {

    private static boolean isContains(String value, String[] keys) {
        for (String key : keys) {
            if (value.contains(key)) {
                return true;
            }
        }
        return false;
    }

    private static void getLog(String table) {
        int length = 1000000;
        for (int i = 0; i < 10000; i++) {
            Connection connection = null;
            try {
                connection = getConn();
                if (null == connection || connection.isClosed()) {
                    return;
                }
                //2.创建statement类对象，用来执行SQL语句！！
                Statement statement = connection.createStatement();
                //要执行的SQL语句
                int start = i * length;
                int end = start + length;
                String sql = "select id,api_key,question,answer from %s where id>%d and id<=%d";
                sql = String.format(sql, table, start, end);
                //3.ResultSet类，用来存放获取的结果集！！
                ResultSet rs = statement.executeQuery(sql);
                String question;
                String answer;
                String apikey;
                int id;
                // 如果检索不到数据，退出
                if (!rs.next()) {
                    rs.close();
                    break;
                } else {
                    rs.beforeFirst();
                }
                while (rs.next()) {
                    try {
                        question = rs.getString("question").replace("\r", "").replace("\n", "").replace("\t", "").trim();
                        answer = rs.getString("answer").replace("\r", "").replace("\n", "").replace("\t", "").trim();
                        apikey = rs.getString("api_key").replace("\r", "").replace("\n", "").replace("\t", "").trim();
                        id = rs.getInt("id");
                        if (StringUtils.isBlank(question) || StringUtils.isBlank(answer)) {
                            continue;
                        }
                        writeToTxt("/mnt/work/snd-faq/bat/faq-qa/" + table + ".txt", String.format("%d\t%s\t%s\t%s", id, apikey, question, answer));
                    } catch (Exception e1) {
                    }
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
        }
        System.out.println(table + "完成！");
    }

    public static void main(String[] args) {
        String[] tables = {"turing_faq_00", "turing_faq_01", "turing_faq_02", "turing_faq_03", "turing_faq_04", "turing_faq_05", "turing_faq_06", "turing_faq_07", "turing_faq_08", "turing_faq_09", "turing_faq_10", "turing_faq_11", "turing_faq_12", "turing_faq_13"};
        for (String table : tables) {
            getLog(table);
        }
    }

    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://common.backend.mysql.tulingapi.com:3308/nlp_faq";
        String username = "nlp_faq";
        String password = "8yxNVhNa7faDPbNR";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void writeToTxt(String path, String content) {
        File file = new File(path);
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file, true);
            writer = new BufferedWriter(fw);
            writer.write(content);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
