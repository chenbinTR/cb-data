package book.kehu.en;

import book.BookUtils;
import book.entity.BookAreaEntity;
import book.entity.BookParams;
import cn.hutool.core.io.FileUtil;

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
     * 待处理的套书文件夹名称集合`
     */
    private static List<String> seriesNameList = Arrays.asList("人教版精通小学英语三起三年级上册(2012.6.1,2012.7.1新疆)");
//    private static List<String> seriesNameList;
//    static{
//        File file = new File("E:\\BOOK_DATA\\已处理\\已修正-加静音段\\英语");
//        seriesNameList = Arrays.asList(file.list());
//    }

    public static void main(String[] args) {
        BookParams bookParams = new BookParams();
        // 遍历每个系列书
        for (String seriesName : seriesNameList) {
            List<String> bookNameList = bookParams.getBookNameList(seriesName);
            // 遍历单本书
            for (String bookName : bookNameList) {
                BookParams bookParam = new BookParams(seriesName, bookName, false);
                // 读取excel，map中key是页码，value是页对应的域s，读取excel的同时，判断excel中数据是否正常，并进行格式的归一化
                String excelFilePath = BookUtils.getExcelFilePath(bookParam.getSOURCE_BOOK_FOLDER());
                Map<String, List<BookAreaEntity>> pageBookEntityMap = ExcelProcessorKehu.readBookAreaEntity(excelFilePath, bookParam);

                if (1 == isCheck) {
                    // 验证音频文件是否存在
                    CheckProcessorKehu.checkVoice(pageBookEntityMap, bookParam);
                } else {
                    // 图片
                    ImgProcessor.imgProcess(bookParam);
                    // 按页合成mp3和文本
                    VoiceProcessorKehu.combinVoiceTextByPage(pageBookEntityMap, bookParam);
                    // 按域生成mp3和文本
                    VoiceProcessorKehu.combinVoiceTextBySegment(pageBookEntityMap, bookParam);

                    //删除临时文件夹
                    FileUtil.del(bookParam.getDEST_AUDIO_FOLDER());
                }
            }
        }
    }


}
