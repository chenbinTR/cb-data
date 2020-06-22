package book;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-09-16
 * @see
 * @since
 */
public class DelDump {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\cb\\Downloads\\绘本QA生成\\绘本数据");
        List<String> fileNames = Arrays.asList(file.list());

        File file2 = new File("C:\\Users\\cb\\Downloads\\绘本QA生成\\绘本数据");
        List<String> fileNames2 = Arrays.asList(file2.list());

    }
}
