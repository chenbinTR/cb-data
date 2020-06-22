package book.ch;

import book.entity.BookAreaEntity;
import book.entity.BookParams;
import book.processornew.CheckProcessor;
import book.processornew.ExcelProcessor;
import book.processornew.ImgProcessor;
import book.processornew.VoiceProcessor;
import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ChenOT
 * @date 2019-12-13
 * @see
 */
public class MainProcessor {
    /**
     * 预检验开关
     */
    private static int isCheck = 0;
    /**
     * 待处理的套书文件夹名称集合
     */
    private static List<String> seriesNameList = Arrays.asList("青岛出版社数学五四制");
//    private static List<String> seriesNameList;
//    static{
//        File file = new File("E:\\BOOK_DATA\\已处理\\已修正-加静音段");
//        seriesNameList = Arrays.asList(file.list());
//    }

    public static void main(String[] args) {
        BookParams bookParams = new BookParams();
        // 遍历每个系列书
        for (String seriesName : seriesNameList) {
            List<String> bookNameList = bookParams.getBookNameList(seriesName);
            // 遍历单本书
            for (String bookName : bookNameList) {
                BookParams bookParam = new BookParams(seriesName, bookName, true);
                // 读取excel，map中key是页码，value是页对应的域s，读取excel的同时，判断excel中数据是否正常，并进行格式的归一化
                String excelFilePath = getExcelFilePath(bookParam.getSOURCE_BOOK_FOLDER());
                Map<String, List<BookAreaEntity>> pageBookEntityMap = ExcelProcessor.readBookAreaEntityCh(excelFilePath, bookParam);

                if (isCheck == 1) {
                    // 验证音频文件是否存在
                    CheckProcessor.checkVoice(pageBookEntityMap, bookParam);
                } else {
                    // 图片
                    ImgProcessor.imgProcess(bookParam);
                    // 按页合成mp3和文本
                    VoiceProcessor.combinVoiceTextByPage(pageBookEntityMap, bookParam);
                    // 按域生成mp3和文本
                    VoiceProcessor.combinVoiceTextBySegment(pageBookEntityMap, bookParam);

                    //删除临时文件夹
                    FileUtil.del(bookParam.getDEST_AUDIO_FOLDER());
                }
            }
        }
    }

    /**
     * 获取excel文件完整路径
     *
     * @param bookFolderPath
     * @return
     */

    private static String getExcelFilePath(String bookFolderPath) {
        File file = new File(bookFolderPath);
        String[] names = file.list();
        String excelFilePath = "";
        for (String name : names) {
            if (name.toLowerCase().contains("xls")) {
                excelFilePath = bookFolderPath + name;
                break;
            }
        }
        return excelFilePath;
    }
}
