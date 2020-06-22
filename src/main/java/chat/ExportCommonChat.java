package chat;

import com.mysql.jdbc.Connection;
import org.apache.commons.lang.StringUtils;
import utils.CbMysqlClientUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ChenOT
 * @date 2020-05-14
 * @see
 * @since
 */
public class ExportCommonChat {
    public static void main(String[] args) {
        getCommonQuestion();
    }
    private static void getCommonQuestion() {
        Connection connection = null;
        try {
            connection = CbMysqlClientUtils.createConnection(CbMysqlClientUtils.ConnectType.PROD_NLP_CHAT);
            if (null == connection || connection.isClosed()) {
                System.out.println("数据库连接失败");
                return;
            }
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM common_chat_answer";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.toString());
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
