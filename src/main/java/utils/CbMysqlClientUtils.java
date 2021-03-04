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
//            conn = (Connection) DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s", SymmetricEncoder.AESDncode(connectType.getHost()), connectType.getPort(), connectType.getDb()), connectType.getUser(), connectType.getPasswd());
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
        PROD_LOG("XkrJ5HkZWzgCXT5AbsHpXA==", "customer", "xinke2003", "3318", "turing_platform_log"),
        // 平台
        DEV_QA_PLATFORM("65DeqWwvk/vYM6r4+1XhKWPPOixTVGblSdUt5DffrsY=", "appuser", "appuser123", "3306", "nlp_chat"),
        PROD_PLATFORM("rqfonOhDYGknjUOV5Xd/qcGGpu/xhfSIf/v617NHslKVCh5h2+JVOtAMMT//PcOK", "turing_platform", "laQ8MWHFnbIx8zxY", "3308", "turing_platform"),
        // 儿童
        PROD_NLP_CHAT("JsFVrR1NL9UHX/XbbAs3ig==", "nlp_chat", "mzyVDO98iySuXiOh", "3306", "nlp_chat"),
        PROD_FAQ("common.backend.mysql.tulingapi.com", "nlp_faq", "8yxNVhNa7faDPbNR", "3308", "nlp_faq");

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
