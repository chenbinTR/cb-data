package chat;

import com.mysql.jdbc.Connection;
import org.apache.commons.lang.StringUtils;
import utils.CbMysqlClientUtils;
import utils.SentenceFormUtils;
import vo.SentenceFormVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-05-17
 * @see
 * @since
 */
public class CommonChat {
    public static void main(String[] args) {
        getCommonQuestion();
    }

    private static String getSentenceForm(String answer) {
        String[] answers = answer.split("\\|");
        List<String> list = new ArrayList<>();
        for (String a : answers) {
            SentenceFormVO sentenceFormVO = SentenceFormUtils.getSentenceForm(a);
            if (sentenceFormVO != null) {
                list.add(sentenceFormVO.getSentenceType() + "-" + sentenceFormVO.getSentenceType());
            }
        }
        if (list.size() == answers.length) {
            return StringUtils.join(list.toArray(), "|");
        } else {
            return null;
        }
    }

    private static void getCommonQuestion() {
        Connection connection = null;
        try {
            connection = CbMysqlClientUtils.createConnection(CbMysqlClientUtils.ConnectType.ALPHA_CHAT);
            if (null == connection || connection.isClosed()) {
                System.out.println("数据库连接失败");
                return;
            }
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM common_chat_answer";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String answer = rs.getString("answer");
                String sentenceForm = getSentenceForm(answer);
                if (StringUtils.isNotBlank(sentenceForm)) {
                    Statement statement1 = connection.createStatement();
                    statement1.execute(String.format("update common_chat_answer set attr='%s' where id=%d", sentenceForm, id));
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
        System.out.println("完成！");
    }
}
