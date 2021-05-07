package utils;

/**
 * @author ChenOT
 * @date 2019-09-05
 * @see
 * @since
 */
public enum  RedisServerEnum {
    DEV_CHAT("192.168.10.33",6379, 2, "nlpturing2016"),
    ALPHA_CHAT("RA/hiaR71pDWmOvuSeqrab24IrTPGYElP4pvSJR9ZOE=",6379, 2, "nlpturing2016"),
    PROD_CHAT("LebHZyyZV+MQ3dZnyaUmAw==",6386, 2, "nlpturing2016"),
    PROD_SHIELD("YwZHUt2GKTDmfbQQ0uBwVXgZNh40KfEFQneX79OAqnQemQtOOWRrlnckTn5nacqb",6393, 0, null),
    PROD_TTS("1NLIii4NIbnK36ydiyVFPA==",6379,2,"nlpturing2016");

    private String host;
    private int port;
    private int database;
    private String password;

    RedisServerEnum(String host, int port, int database, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.password = password;
    }

    String getHost(){
        return host;
    }
    int getPort(){
        return port;
    }
    int getDatabase(){
        return database;
    }
    String getPassword(){
        return password;
    }

}
