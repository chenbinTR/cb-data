import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import utils.SymmetricEncoder;
import utils.Utils;

import java.io.File;
import java.util.List;

/**
 * @author ChenOT
 * @Description: TODO
 * @date 2021/2/25
 */
public class Test1 {
    private static String[] urls = {"http://10.67.2.171:9201/nlp_faq_cs/doc", "http://10.31.184.43:9201/nlp_faq_cs/doc", "http://10.31.144.222:9201/nlp_faq_cs/doc"};
    private static void tttt() {
        int index = RandomUtils.nextInt(0, 3);
        System.out.println(index);
        System.out.println(urls[index]);

    }

    public static void main(String[] args) {
        System.out.println(SymmetricEncoder.AESDncode("bIQlPORw3/vNq0GINmL5kA=="));
    }
}
