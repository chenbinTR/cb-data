package redis;

import redis.clients.jedis.Jedis;
import utils.RedisUtil;

import java.util.Collections;

/**
 * redis分布式锁的正确姿势
 *
 * @author ChenOT
 * @date 2020-01-17
 * @see
 * @since
 */
public class RedisLockUtils {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        Jedis jedis = RedisUtil.getJedis();
        boolean isSuccess = false;
        if (null != jedis) {
            try {
                String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
                if (LOCK_SUCCESS.equals(result)) {
                    isSuccess = true;
                }
            } catch (Exception e) {

            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        return isSuccess;
    }


    /**
     * 释放分布式锁
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        boolean isSuccess = false;
        Jedis jedis = RedisUtil.getJedis();
        try {
            if(null !=jedis){
                Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
                if (RELEASE_SUCCESS.equals(result)) {
                    isSuccess = true;
                }
            }
        }catch (Exception e){

        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return isSuccess;
    }
}
