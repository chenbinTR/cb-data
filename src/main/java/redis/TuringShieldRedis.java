package redis;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import utils.RedisServerEnum;
import utils.RedisUtil;

import java.util.Set;

/**
 * @author ChenOT
 * @date 2019-09-05
 * @see
 * @since
 */
public class TuringShieldRedis {
    private static Jedis jedis;

    public static void main(String[] args) {
        RedisUtil.init(RedisServerEnum.PROD_SHIELD);
        jedis = RedisUtil.getJedis();
        testString();
    }

    private static void testString() {
        // get all keys
        Set<String> keys = jedis.keys("*");
        System.out.println(keys.size());
        String value=null;
        int count = 0;
        for(String key:keys){
            count++;
            try{
                value = jedis.get(key);
                JSONObject jsonObject = JSONObject.parseObject(value);
                int violenceInfo = jsonObject.getJSONObject("violenceInfo").getInteger("level");
                int pornoInfo = jsonObject.getJSONObject("pornoInfo").getInteger("level");
                int sensitiveInfo = jsonObject.getJSONObject("sensitiveInfo").getInteger("level");
                if(sensitiveInfo == 11){
                }else{
                    jedis.del(key);
                }
//                if(key.indexOf("public-oss")>-1){
//                    jedis.del(key);
//                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println(key);
                System.out.println(value);
            }
            if(count %10000 == 0){
                System.out.println(count);
            }
        }
        System.out.println("over");
        jedis.close();
    }
    private static boolean isFilered(int value){
        if(value > 0 && value != 10){
            return true;
        }
        return false;
    }
}
