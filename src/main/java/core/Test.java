package core;


import utils.SymmetricEncoder;
import utils.Utils;

/**
 * @author ChenOT
 * @date 2019-03-12
 * @see
 * @since
 */
public class Test {
    public static void main(String[] args) {

        //测试用例   刘德华是谁  ---》 百科
        //测试用例   打电话给爸爸  ---》 打电话（测试userid不要修改）
        //测试用例   讲个笑话  ---》 笑话
        //测试用例   播放音乐  ---》 音乐
        String req = "{\"user_account\":\"tuling\",\"user_id\":\"69402725\",\"cmd\":\"现在几点\",\"expected_info\":{\"expected_appid\":[],\"expected_parsetype\":[1,2,3,6,7],\"machine_flag\":0,\"expected_domainid\":[]},\"appid_list\":{\"appid_flag\":0,\"appid\":[1800210,1000401],\"cate_id\":[1100400,1100100,1000200,1800200,2203100,1000100,40000,2201500,900100,1200100,700300,1400200,2100100,1600100,400400,2300100,400500,2202500,700200,2000100,1800400,1000400]},\"negative\":0,\"context_info\":{\"last_people_name\":\"\",\"history_info\":[{\"last_cmd\":\"北京天气怎么样\",\"last_answer\":\"\",\"last_index\":1,\"parse_result\":{\"last_parse_type\":3,\"last_app_id\":1100402,\"last_app_name\":\"EDU.TRANSLATION.SINGLECHTOEN\",\"last_smt\":{\"trans_keyword\":\"111\"}}}],\"auto_learn\":0,\"context_flag\":0,\"last_location_to\":\"\"},\"location_info\":{\"city\":\"北京\",\"latitude\":\"39.45492\",\"longitude\":\"119.239293\",\"nearest_poi_name\":\"上地环岛南\",\"province\":\"北京\",\"street\":\"信息路\"}}";

        try {
            System.out.println(Utils.httpPost( req, SymmetricEncoder.AESDncode("vcDvbB/EaJZbOJ6Vdck4ytCmP5gl2NvGVhORZqz3IOlklHAlrIZXLihEbSKla1j8"), "gbk"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
