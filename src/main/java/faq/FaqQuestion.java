package faq;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Connection;
import org.apache.commons.lang3.StringUtils;
import utils.CbMysqlClientUtils;
import utils.UtilsMini;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class FaqQuestion {
    public static void main(String[] args) {
//        getFaq();
        getFaqSimi();
    }

    private static String path = "/home/developer/bat/";

    private static void getFaqSimi() {
        List<String> lines = UtilsMini.readFileToList(path + "mapper.txt");
        for (String line : lines) {
            String apikey = line.split("\t")[0];
            String tableName = line.split("\t")[1].replace("turing_faq_", "turing_faq_simi_");
            Connection connection = null;
            try {
                connection = CbMysqlClientUtils.createConnection(CbMysqlClientUtils.ConnectType.PROD_FAQ);
                Statement statement = connection.createStatement();
                String sql = "SELECT * FROM " + tableName + " where api_key='" + apikey + "' and state=1";
                ResultSet rs = statement.executeQuery(sql);
                while (rs != null && rs.next()) {
                    String question = rs.getString("question");
                    if (StringUtils.isBlank(question) || question.startsWith("相似问法")) {
                        continue;
                    }
                    int id = rs.getInt("id");
                    int labelId = rs.getInt("label_id");
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("id", id);
                    jsonObject.put("question", question);
                    jsonObject.put("label_id", labelId);
                    jsonObject.put("table_name", tableName);
                    jsonObject.put("account", apikey);

                    UtilsMini.writeToTxt(path + "faq_simi.txt", jsonObject.toJSONString());
                }
                rs.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                UtilsMini.writeToTxt(path + "mapper_error_simi.txt", line);
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

        System.out.println("完成！");
    }

    private static void getFaq() {
        List<String> lines = UtilsMini.readFileToList(path + "mapper.txt");
        for (String line : lines) {
            String apikey = line.split("\t")[0];
            String tableName = line.split("\t")[1];
            Connection connection = null;
            try {
                connection = CbMysqlClientUtils.createConnection(CbMysqlClientUtils.ConnectType.PROD_FAQ);
                Statement statement = connection.createStatement();
                String sql = "SELECT * FROM " + tableName + " where api_key='" + apikey + "' and state=1";
                ResultSet rs = statement.executeQuery(sql);
                while (rs != null && rs.next()) {
                    String question = rs.getString("question");
                    int id = rs.getInt("id");
                    int labelId = rs.getInt("label_id");
                    String keywords = rs.getString("keywords");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("question", question);
                    jsonObject.put("id", id);
                    jsonObject.put("label_id", labelId);
                    jsonObject.put("keywords", keywords);
                    jsonObject.put("table_name", tableName);
                    jsonObject.put("account", apikey);

                    UtilsMini.writeToTxt(path + "faq.txt", jsonObject.toJSONString());
                }
                rs.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                System.out.println(e.getMessage());
                UtilsMini.writeToTxt(path + "mapper_error.txt", line);
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

        System.out.println("完成！");
    }
}
