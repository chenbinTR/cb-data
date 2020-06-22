package redis;

import redis.clients.jedis.Jedis;
import utils.RedisServerEnum;
import utils.RedisUtil;

import java.util.*;

public class TestRedis {
    private static String qids ="4993596";
    private static String keys = "dialogue:424242";
    private static Jedis jedis;

    public static void main(String[] args) {
        RedisUtil.init(RedisServerEnum.PROD_CHAT);
        jedis = RedisUtil.getJedis();
        testString();
    }

    /**
     * redis操作字符串
     */
    private static void testString() {
//        String[] qidList = qids.split("\\|");
//        for(String qid:qidList){
//            String key = "dialogue:"+qid.trim();
//            System.out.println(jedis.get(key));
//            System.out.println(jedis.del(key));
//        }
//        String[] keyss = keys.split("\\|");
        System.out.println(jedis.flushDB());
//        for (String s : keyss) {
//            System.out.println(jedis.get(s));
//            System.out.println(jedis.del(s));
//        }
//        System.out.println(jedis.set("dialogue:4993535","[{\"aid\":-122323,\"ext\":{},\"id\":\"5e58c41edb169759549605d5\",\"infos\":[{\"content\":\"武汉加油，中加油。\",\"type\":0}],\"qid\":4993535,\"score\":100.0,\"tags\":{\"sentence_form\":\"陈述句\",\"sentence_form_score\":\"0.9997557401657104\"},\"type\":\"dialogue\"}]"));
//        System.out.println(jedis.get("dialogue:4993535"));
//        System.out.println(jedis.flushDB());
        //添加数据
//        jedis.set("name", "youcong");
//        Set<String> set = jedis.keys("phrase*");
//        for (String str:set){
//            System.out.println(jedis.del(str));
//        }
//
//        Set<String> dialogueSet = jedis.keys("*");
//        for(String str:dialogueSet){
//            System.out.println(jedis.del(str));
//        }


//        System.out.println(jedis.del("dialogue:137676"));

//        Set<String> center_word = jedis.keys("center_word*");
//        for(String str:center_word){
//            System.out.println(jedis.del(str));
//        }

        if (jedis != null) {
            jedis.close();
        }
        System.out.println("完成");
    }


    /**
     * redis操作map集合
     */
    public void testMap() {
        //添加数据
        Map<String, String> map = new HashMap<String, String>();

        map.put("name", "yc");
        map.put("age", "22");
        map.put("qq", "1933108196");
        jedis.hmset("user", map);

        //取出users中的Name,执行结果:[minxr]-->注意结果是一个泛型的List
        //第一个参数是存入redis中map对象的key,后面跟的是放入map中对象的key,后面的key可以是多个，是可变的
        List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
        System.out.println(rsmap);


        //删除map中的某个键值
        jedis.hdel("user", "age");
        System.out.println(jedis.hmget("user", "age"));//因为删除了，所以返回的是Null
        System.out.println(jedis.hlen("user"));//返回key为user的键中存放的值的个数2
        System.out.println(jedis.exists("user"));//是否存在key为user的记录，返回true
        System.out.println(jedis.hkeys("user"));//返回map对象中的所有key
        System.out.println(jedis.hvals("user"));//返回map对象中的所有value

        Iterator<String> iter = jedis.hkeys("user").iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + jedis.hmget("user", key));
        }

    }

    /**
     * redis操作List集合
     */
    public void testList() {
        //开始前，先移除所有的内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework", 0, -1));

        //先向key java framework 中存放三条数据
        jedis.lpush("java framework", "spring");
        jedis.lpush("java framework", "struts");
        jedis.lpush("java framework", "hibernate");

        //再取出所有数据jedis.lrange是按范围取出
        //第一个是key,第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java framework", 0, -1));

        jedis.del("java framework");
        jedis.rpush("java framework", "spring");
        jedis.rpush("java framework", "struts");
        jedis.rpush("java framework", "hibernate");
        System.out.println(jedis.lrange("java framework", 0, -1));


    }


    /**
     * redis操作set集合
     */

    public void testSet() {

        //添加
        jedis.sadd("user", "liuling");
        jedis.sadd("user", "xinxin");
        jedis.sadd("user", "ling");
        jedis.sadd("user", "zhangxinxin");
        jedis.sadd("user", "who");

        //删除
        jedis.srem("user", "who");
        System.out.println(jedis.smembers("user"));//获取所有加入的value
        System.out.println(jedis.sismember("user", "who"));//判断who是否是user集合的元素
        System.out.println(jedis.srandmember("user"));
        System.out.println(jedis.scard("user"));//返回集合的元素个数


    }


    /**
     * redis排序
     */
    public void testSort() {
        //jedis 排序
        //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的)
        jedis.del("a");//先清除数据，再加入数据进行测试
        jedis.rpush("a", "1");
        jedis.lpush("a", "6");
        jedis.lpush("a", "3");
        jedis.lpush("a", "9");
        System.out.println(jedis.lrange("a", 0, -1));
        System.out.println(jedis.sort("a"));//[1,3,6,9] //输入排序后结果
        System.out.println(jedis.lrange("a", 0, -1));
    }


    /**
     * redis连接池
     */

    public void testRedisPool() {
        RedisUtil.getJedis().set("newname", "test");
        System.out.println(RedisUtil.getJedis().get("newname"));
    }


}