import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import utils.ExcelEntity;
import utils.SymmetricEncoder;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author ChenOT
 * @date 2019-06-20
 * @see
 * @since
 */
public class Test {
    /**
     * 促销活动起始时间，需要根据用户创建时间来生效
     */
    private static Date MEMBER_CREATE_DATE;
    private static Date createDate;
    private static SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws IOException {
        System.out.println(SymmetricEncoder.AESEncode("1"));
        Float f1 = 0.5672F;
        Float f2 = 0.5671F;

        for (int i=0;i<10;i++){
            System.out.println(f1.compareTo(f2));
        }

        int value = -10;
        int a = 100;
        System.out.println(a+value);

//        ThreadLocalRandom.current().nextInt(0);

    }
}
