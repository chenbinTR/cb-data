package book.wotewode;

import com.alibaba.fastjson.JSON;
import com.iflytek.msp.cpdb.lfasr.client.LfasrClientImp;
import com.iflytek.msp.cpdb.lfasr.exception.LfasrException;
import com.iflytek.msp.cpdb.lfasr.model.LfasrType;
import com.iflytek.msp.cpdb.lfasr.model.Message;

/**
 * @author ChenOT
 * @date 2020-01-08
 * @see
 * @since
 */
public class XunfeiAsrClient {
    private static LfasrClientImp lc;
    static {
        try {
            lc = LfasrClientImp.initLfasrClient();
        } catch (LfasrException e) {
            // 初始化异常，解析异常描述信息
            Message initMsg = JSON.parseObject(e.getMessage(), Message.class);
            System.err.println("ecode=" + initMsg.getErr_no());
            System.err.println("failed=" + initMsg.getFailed());
        }
    }

    public static LfasrClientImp getClient(){
        return lc;
    }
}
