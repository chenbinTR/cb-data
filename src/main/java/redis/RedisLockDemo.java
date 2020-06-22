package redis;

import utils.RedisServerEnum;
import utils.RedisUtil;

/**
 * @author ChenOT
 * @date 2020-01-17
 * @see
 * @since
 */
public class RedisLockDemo {
    private static final String KEY = "key";
    static{
        RedisUtil.init(RedisServerEnum.DEV_CHAT);
    }
    public static void main(String[] args) {
        new Thread(new RedisLockTask(KEY, "make")).start();
        new Thread(new RedisLockTask( KEY, "love")).start();
    }
}
