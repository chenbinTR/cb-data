package threadpattern;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author ChenOT
 * @Description: TODO
 * @date 2021/2/25
 */
public class Test1 {
    public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void process() {
        ExcelReader excelReader = ExcelUtil.getReader(new File("E:\\汉字-音意.xlsx"));
        List<Map<String, Object>> readAll = excelReader.readAll();
        for (Map<String, Object> map : readAll) {
            try {
                String id = MapUtils.getString(map, "id", "").trim();
                String word = MapUtils.getString(map, "字", "").trim();
                String pinyin = MapUtils.getString(map, "拼音", "").trim();
                String explain = MapUtils.getString(map, "释义（|）", "").trim();
                String combinWord = MapUtils.getString(map, "组词（|）", "").trim();
                List<String> strings = new ArrayList<>();
                if (StringUtils.isNotBlank(combinWord)) {
                    String[] items = combinWord.split("\\|");
                    for (String item : items) {
                        if (StringUtils.isBlank(item) || item.length() > 4) {
                            continue;
                        }
                        strings.add(item.trim());
                    }
                }
                if (CollectionUtils.isNotEmpty(strings)) {
                    combinWord = StringUtils.join(strings.toArray(), "|");
                }
                Utils.writeToTxt("E:\\111.txt", id, word, pinyin, explain, combinWord);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        int taskCount = 10;
        long startTime = System.currentTimeMillis();
        List<Future<String>> futures = new ArrayList<>();
        for(int i=0; i<taskCount; i++){
            final int taskId = i;
            Future<String> f = ThreadContainer.getExecutor().submit(() -> test(taskId));
            futures.add(f);
        }
        for (Future<String> future : futures) {
            try {
                future.get(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        System.out.println("main 执行完成 "+(System.currentTimeMillis() - startTime));

    }

    private static String test(int taskId) {
        try {
            System.out.println(Thread.currentThread().getName() + "开始执行"+taskId);
            Thread.sleep(2001);
            System.out.println(Thread.currentThread().getName() + "执行完成"+taskId);
            return "21";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "33";
    }

}
