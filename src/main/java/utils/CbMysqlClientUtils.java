package utils;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author ChenOT
 * @date 2019-05-17
 * @see
 * @since
 */
public class CbMysqlClientUtils {
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    /**
     * 建立连接
     *
     * @param connectType
     * @return
     */
    public static Connection createConnection(ConnectType connectType) {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = (Connection) DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s", connectType.getHost(), connectType.getPort(), connectType.getDb()), connectType.getUser(), connectType.getPasswd());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public enum ConnectType {
        // 日志
        PROD_LOG("101.201.73.129", "customer", "xinke2003", "3318", "turing_platform_log"),
        // 聊天
        ALPHA_CHAT("114.55.245.227", "tuling123", "0j8yzBepKFI3XcoW", "3306", "nlp_chat"),
        // 平台
        DEV_PLATFORM("web.mysql.tuling.com", "appuser", "appuser123", "3306", "turing_platform"),
        DEV_QA_PLATFORM("web.mysql.tuling.com", "appuser", "appuser123", "3306", "nlp_chat"),
        BETA_PLATFORM("120.27.160.234", "appuser", "appuser123", "3306", "turing_platform"),
        PROD_PLATFORM("common.backend.mysql.tulingapi.com", "turing_platform", "laQ8MWHFnbIx8zxY", "3308", "turing_platform"),
        // BOOK_DEV
        BOOK_DEV("new2.mysql.tuling.com", "appuser", "appuser123", "3306", "turing-universe-story"),
        // 儿童
        PROD_CHILD_ASK("qa.turingos.mysql.tulingapi.com","nlp_qa", "GiDiIfHjQw8kF6dF", "3306", "nlp_qa"),
        PROD_NLP_CHAT("10.31.26.194","nlp_chat", "mzyVDO98iySuXiOh", "3306", "nlp_chat");

        private String host;
        private String user;
        private String passwd;
        private String port;
        private String db;

        public String getHost() {
            return host;
        }

        public String getUser() {
            return user;
        }

        public String getPasswd() {
            return passwd;
        }

        public String getPort() {
            return port;
        }

        public String getDb() {
            return db;
        }

        ConnectType(String host, String user, String passwd, String port, String db) {
            this.host = host;
            this.user = user;
            this.passwd = passwd;
            this.port = port;
            this.db = db;
        }

    }
}
