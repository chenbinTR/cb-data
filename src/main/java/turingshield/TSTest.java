package turingshield;

import utils.Utils;

import java.util.List;
import java.util.concurrent.*;

/**
 * @author ChenOT
 * @date 2019-08-08
 * @see
 * @since
 */
public class TSTest {
    private static ExecutorService service = new ThreadPoolExecutor(
            3, 3,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());

    private final static String PATH = "Q:\\Colleague\\张清\\图灵盾\\";
    private final static String TEST_FILE = PATH + "测试集有标记内容.txt";
    private final static String RESULT_FILE = PATH + "测试集有标记内容_result_v3_dev.txt";

    public static void main(String[] args) {
        List<String> stringList = Utils.readFileToList(TEST_FILE);
        long time1 = System.currentTimeMillis();
        for (String simi : stringList) {
            try {
                TuringShieldTask task = new TuringShieldTask(simi, TuringShieldConfig.DEV_URL_v3, RESULT_FILE);
                Future<String> result = service.submit(task);
//                System.out.println(result.get());
//                Utils.writeToTxt(RESULT_FILE, result.get());
            } catch (Exception e) {
                e.printStackTrace();
//                Utils.writeToTxt(RESULT_FILE, String.format("%s\t%d\t%d\t%d", simi, 0, 0, 0));
            }
        }
        System.out.println(System.currentTimeMillis() - time1);
        service.shutdown();
    }
}
