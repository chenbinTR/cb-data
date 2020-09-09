package book.kehu.en;

import book.entity.BookParams;
import org.apache.commons.lang.StringUtils;
import utils.Utils;

import java.io.File;
import java.util.Arrays;

/**
 * 图片处理
 * 将封面和内页图片拷贝到结果文件夹，并根据页码重命名
 * @author ChenOT
 * @date 2019-12-16
 * @see
 */
public class ImgProcessor {
    /**
     * 图片处理
     *
     * @param bookParams 书文件夹名字
     */
    public static void imgProcess(BookParams bookParams) {
        System.out.println(String.format("[%s][图片处理]", bookParams.getBookName()));
        // 封面
        coverImgProcess(bookParams);
        // 内页
        insideImgProcess(bookParams);
    }

    /**
     * 内页图片处理
     * @param bookParams
     */
    private static void insideImgProcess(BookParams bookParams){
        // 内页图片文件夹
        String imgFolderPath = bookParams.getSOURCE_IMG_FOLDER();
        File imgFolder = new File(imgFolderPath);
        String[] imgNames = imgFolder.list();
        // 结果文件夹
        String destImgFolder = bookParams.getDEST_BOOKING_MERGE_FOLDER();
        for (String imgName : imgNames) {
            if (!imgName.toUpperCase().contains(".JPG")) {
                continue;
            }
            int pageNum = ExcelProcessor.getPageNum(imgName.toUpperCase().replace(".JPG", ""));
            // 拷贝+重命名
            Utils.combinFile(Arrays.asList(imgFolderPath + imgName), destImgFolder + pageNum + ".jpg");
        }
    }

    /**
     * 封面图片处理
     *
     * @param bookParams
     */
    private static void coverImgProcess(BookParams bookParams) {
        String faceImgPath = null;
        // 封面图片所在文件夹
        String bookFolderPath = bookParams.getSOURCE_BOOK_FOLDER();
        // 遍历文件夹中所有文件，找到封面图片
        File file = new File(bookFolderPath);
        String[] names = file.list();
        for (String name : names) {
            if (name.toLowerCase().endsWith(".jpg")) {
                faceImgPath = name;
                break;
            }
        }
        if(StringUtils.isBlank(faceImgPath)){
            return;
        }
        // 目标文件夹
        String destFacePath = bookParams.getDEST_BOOKIMG_FOLDER();
        // 执行拷贝
        Utils.combinFile(Arrays.asList(bookFolderPath + faceImgPath), destFacePath + "0.jpg");
    }
}
