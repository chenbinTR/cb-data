package utils;

/**
 * @author ChenOT
 * @date 2019-09-05
 * @see
 * @since
 */
public enum  RedisServerEnum {
    DEV_CHAT("6383.redis.tuling.com",6383, 0, "nlpturing2016"),
    ALPHA_CHAT("redis6379.redis.tulingapi.com",6379, 2, "nlpturing2016"),
    PROD_CHAT("10.25.134.248",6386, 2, "nlpturing2016"),
    PROD_SHIELD("turingshield-6393.redis.tulingapi.com",6393, 0, null),
    PROD_TTS("10.31.144.30",6379,2,"nlpturing2016");

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
