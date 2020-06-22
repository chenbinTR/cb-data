package platform;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 平台生成万能密码
 * @author ChenOT
 * @date 2019-06-20
 * @see
 * @since
 */
public class CreateUniversalPassword {
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("qCkkKayF4YeYQE2txz,MFH;MunHUin*2" + DateFormatUtils
                .format(new Date(), "yyyyMM")));
    }
}
