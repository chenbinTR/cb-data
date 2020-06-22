package utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static JedisPool jedisPool = null;
    /**
     * 初始化Redis连接池
     */
    public static void init(RedisServerEnum redisServer){
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(100);
            config.setMaxIdle(100);
            config.setMinIdle(50);
            jedisPool = new JedisPool(config, redisServer.getHost(), redisServer.getPort(), 200*1000, redisServer.getPassword(), redisServer.getDatabase());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取Jedis实例
     */
    public static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}