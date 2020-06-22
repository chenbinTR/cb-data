package redis;

import redis.clients.jedis.Jedis;

/**
 * @author ChenOT
 * @date 2020-01-19
 * @see
 * @since
 */
public class RedisLockTask implements Runnable {
    private String key;
    private String name;
    public RedisLockTask( String key, String name){
        this.key = key;
        this.name = name;
    }
    @Override
    public void run() {
        boolean isGetLock;
        while(!(isGetLock = RedisLockUtils.tryGetDistributedLock( key, name, 5))){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            System.out.println(name+" get lock false");
        }
        if(isGetLock){
            RedisLockUtils.tryGetDistributedLock( key, name, 5);
            System.out.println(name+" get lock success and is working");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
        }
        RedisLockUtils.releaseDistributedLock( key, name);
    }
}
