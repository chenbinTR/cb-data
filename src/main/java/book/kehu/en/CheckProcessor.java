package book.kehu.en;

import book.BookUtils;
import book.constant.CoordinateConstant;
import book.entity.AsmEntity;
import book.entity.AsmInfoEntity;
import book.entity.BookAreaEntity;
import book.entity.BookParams;
import cn.hutool.core.util.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * @author wangsx
 * @Title: book.processor.CheckProcessor
 * @Description: TODO
 * @date 2019/12/23
 */
public class CheckProcessor {
    /**
     * 封面音频、文字处理
     *
     * @param bookParams
     */
    private static void checkFaceVoice(BookParams bookParams) {
//        String faceMp3Folder = String.format(PathConstant.SOURCE_BOOK_FOLDER, bookFolderName);
        String faceMp3Folder = bookParams.getSOURCE_BOOK_FOLDER();
        File file = new File(faceMp3Folder);
        String[] names = file.list();
        String faceMp3FilePath = "";
        for (String name : names) {
            if (name.toLowerCase().endsWith("mp3")) {
                faceMp3FilePath = faceMp3Folder + name;
                break;
            }
        }
        if (StringUtils.isBlank(faceMp3FilePath)) {
            System.err.println(String.format("[%s][封面音频不存在]", bookParams.getBookName()));
        }
    }

    /**
     * 按页合成音频文件
     * 按页合成文本文件
     *
     * @param bookEntityMap  读取每本书的excel后的到的key为page，value为域、音频等内容list的map
     * @param bookParams 书文件夹名字
     */
    public static void checkVoice(final Map<String, List<BookAreaEntity>> bookEntityMap, BookParams bookParams) {
        System.out.println(bookParams.getBookName());
        // 判断封面音频
        checkFaceVoice(bookParams);
        // 处理内页音频
        for (String page : bookEntityMap.keySet()) {
            // 获取数字页码
            int pageNum = ExcelProcessor.getPageNum(page);
            // 单页文本
            for (BookAreaEntity bookEntity : bookEntityMap.get(page)) {
                if (StringUtils.isNotBlank(bookEntity.getEnId())) {

//                    if (!BookUtils.isFileExist(String.format(PathConstant.SOURCE_VOICE_FOLDER, bookFolderName) + bookEntity.getEnId() + ".mp3")) {
                    if (!BookUtils.isFileExist(bookParams.getSOURCE_VOICE_FOLDER() + bookEntity.getEnId() + ".mp3")) {
                        System.err.println(String.format("[%s][英文-音频不存在][%s]", bookParams.getBookName(), bookEntity.getEnId()));
                    }
                    if (StringUtils.isNotBlank(bookEntity.getChId())) {
//                        if (!BookUtils.isFileExist(String.format(PathConstant.SOURCE_VOICE_FOLDER, bookFolderName) + bookEntity.getChId() + ".mp3")) {
                        if (!BookUtils.isFileExist(bookParams.getSOURCE_VOICE_FOLDER() + bookEntity.getChId() + ".mp3")) {
                            System.err.println(String.format("[%s][中文-音频不存在][%s]", bookParams.getBookName(), bookEntity.getChId()));
                        }
                    }
                }
            }
        }
        checkSegmentVoice(bookEntityMap, bookParams);
        checkImg(bookParams);
    }

    /**
     * 将一本书，按域生成音频
     *
     * @param bookEntityMap  整本书的内容
     * @param bookParams 书文件夹名称
     */
    private static void checkSegmentVoice(Map<String, List<BookAreaEntity>> bookEntityMap, BookParams bookParams) {
        int index = -1;
        // 整本书，按页遍历
        for (String page : bookEntityMap.keySet()) {
            // 当前页码转换为整数
            int pageNum = ExcelProcessor.getPageNum(page);
            // 当前页面对应的asm文件（一对一）
            String asmFilePath = bookParams.getSOURCE_ASM_FOLDER() + page + ".ASM";
            AsmInfoEntity asmInfoEntity = AsmProcessor.readAsmFile(asmFilePath);
            Map<Integer, List<AsmEntity>> asmMap = asmInfoEntity.getAsmEntityMap();
            int type = asmInfoEntity.getType();
            // 当前页面对应的图片（一对一）
            String imgFilePath = bookParams.getSOURCE_IMG_FOLDER() + page + ".jpg";
            //图片分辨率
            try {
                FileInputStream in = new FileInputStream(new File(imgFilePath));
                BufferedImage sourceImg = ImageIO.read(in);
                if (sourceImg == null) {
                    System.err.println(String.format("[读取图片出错][%s]", imgFilePath));
                }
            } catch (Exception e) {
                System.err.println(String.format("[读取图片出错][%s]", imgFilePath));
                e.printStackTrace();
            }
            // 当前页的域（excel中的行）
            List<BookAreaEntity> bookEntities = bookEntityMap.get(page);
            // 根据每一页的框号，找到对应的矩形坐标，生成MP3文件名
            // bookEntity 为excel中的一行
            for (BookAreaEntity bookEntity : bookEntities) {
                // asm文件中，一个框号，可能对应多个域（相当于excel中的行）——框号，对应多个音频文件——音频内容相同，域坐标不同）
                int kuangNum = 0;
                try{
                    kuangNum = Integer.valueOf(bookEntity.getAreaId());
                }catch (Exception e){
                    System.err.println(String.format("[%s][框号转数字错误][%s]",bookParams.getBookName(), bookEntity.toString()));
                    continue;
                }


                List<AsmEntity> asmEntities = asmMap.get(kuangNum);
                if (CollectionUtils.isEmpty(asmEntities)) {
                    System.err.println(String.format("[%s]无asm信息[%s]", bookParams.getBookName(), bookEntity));
                    continue;
                }
                for (AsmEntity asmEntity : asmEntities) {
                    index++;
                    String name = String.format("%d-%s-%s-%s-%s-%d"
                            , pageNum
                            , NumberUtil.roundStr((double) CoordinateConstant.getCoordinateValue(asmEntity.getTopLeftX(), type) / (double) 1, 6)
                            , NumberUtil.roundStr((double) CoordinateConstant.getCoordinateValue(asmEntity.getTopLeftY(), type) / (double) 1, 6)
                            , NumberUtil.roundStr((double) CoordinateConstant.getCoordinateValue(asmEntity.getLowerRightX(), type) / (double) 1, 6)
                            , NumberUtil.roundStr((double) CoordinateConstant.getCoordinateValue(asmEntity.getLowerRightY(), type) / (double) 1, 6)
                            , index);
                }
            }
        }
    }

    /**
     * 图片处理
     *
     * @param bookParams 书文件夹名字
     */
    private static void checkImg(BookParams bookParams) {
        // 封面url
        String facePicpath = null;
//        String bookFolderPath = String.format(PathConstant.SOURCE_BOOK_FOLDER, bookFolderName);
        String bookFolderPath = bookParams.getSOURCE_BOOK_FOLDER();
        File file = new File(bookFolderPath);
        String[] names = file.list();
        for (String name : names) {
            if (name.toLowerCase().endsWith(".jpg")) {
                facePicpath = name;
                break;
            }
        }
        // 封面结果
        if (StringUtils.isBlank(facePicpath)) {
            System.err.println(String.format("[%s][封面图片不存在]", bookParams.getBookName()));
        }

        // 内容文件夹
//        String imgFolderPath = String.format(PathConstant.SOURCE_IMG_FOLDER, bookFolderName);
        String imgFolderPath = bookParams.getSOURCE_IMG_FOLDER();
        File picFolder = new File(imgFolderPath);
        String[] picNames = picFolder.list();
        // 结果文件夹
        for (String picName : picNames) {
            if (!picName.toUpperCase().contains(".JPG")) {
                System.out.println(picName);
                continue;
            }
            int pageNum = ExcelProcessor.getPageNum(picName.toUpperCase().replace(".JPG", ""));
        }
    }
}
