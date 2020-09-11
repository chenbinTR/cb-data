package book.boer.en;

import book.constant.CoordinateConstant;
import book.entity.AsmEntity;
import book.entity.AsmInfoEntity;
import book.entity.BookAreaEntity;
import book.entity.BookParams;
import cn.hutool.core.util.NumberUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 音频处理
 *
 * @author ChenOT
 * @date 2019-12-16
 * @see
 */
public class VoiceProcessor {
    /**
     * 封面音频、文字处理
     *
     * @param bookParams
     */
    private static void faceVoiceTextProcess(BookParams bookParams) {
        String faceMp3Folder = bookParams.getSOURCE_BOOK_FOLDER();
        File file = new File(faceMp3Folder);
        String[] names = file.list();
        String faceMp3FilePath = "";
        String textMp3Content = "";
        for (String name : names) {
            if (name.toLowerCase().endsWith("mp3")) {
                faceMp3FilePath = faceMp3Folder + name;
                textMp3Content = name.substring(0, name.indexOf("."));
                break;
            }
        }
        // 如果封面音频存在
        if (StringUtils.isNotBlank(faceMp3FilePath)) {
            String destEnFaceMa3FilePath = bookParams.getDEST_AUDIO_AUDIOBYPAGE_FOLDER() + "0.mp3";
            String destChFaceMa3FilePath = bookParams.getDEST_AUDIO_AUDIOBYPAGE_CH_FOLDER() + "0.mp3";
            Utils.combinMp3File(Arrays.asList(faceMp3FilePath), destChFaceMa3FilePath);
            if (!bookParams.isCh()) {
                Utils.combinMp3File(Arrays.asList(faceMp3FilePath), destEnFaceMa3FilePath);
                Utils.writeToTxt(bookParams.getDEST_TEXT_TEXTBYPAGE_FOLDER() + "0.txt", textMp3Content);
            }
            Utils.writeToTxt(bookParams.getDEST_TEXT_TEXTBYPAGE_CH_FOLDER() + "0.txt", textMp3Content);
        }
    }

    /**
     * 按页合成音频文件
     * 按页合成文本文件
     *
     * @param bookEntityMap 读取每本书的excel后的到的key为page，value为域、音频等内容list的map
     * @param bookParams    书文件夹名字
     */
    public static void combinVoiceTextByPage(final Map<String, List<BookAreaEntity>> bookEntityMap, BookParams bookParams) {
        System.out.println(String.format("[%s][按页合成音频+文字]", bookParams.getBookName()));
        // 处理封面音频
        faceVoiceTextProcess(bookParams);
        // 处理内页音频
        for (String page : bookEntityMap.keySet()) {
            // 获取数字页码
            int pageNum = ExcelProcessor.getPageNum(page);
            List<String> enIdList = new ArrayList<>();
            // 单页音频合成
            List<String> sourceEnMp3FileList = new ArrayList<>();
            List<String> sourceChMp3FileList = new ArrayList<>();
            // 单页文本
            StringBuilder sbEn = new StringBuilder();
            StringBuilder sbCh = new StringBuilder();
            for (BookAreaEntity bookEntity : bookEntityMap.get(page)) {
                if (StringUtils.isNotBlank(bookEntity.getEnId()) && !enIdList.contains(bookEntity.getEnId())) {
                    enIdList.add(bookEntity.getEnId());
                    if (StringUtils.isNotBlank(bookEntity.getEnContent())) {
                        sbEn.append(bookEntity.getEnContent() + "\r\n");
                    }
                    if (StringUtils.isNotBlank(bookEntity.getChContent())) {
                        sbCh.append(bookEntity.getChContent() + "\r\n");
                    }
                    sourceEnMp3FileList.add(bookParams.getSOURCE_VOICE_FOLDER() + bookEntity.getEnId() + ".mp3");
                    if (StringUtils.isNotBlank(bookEntity.getChId())) {
                        sourceChMp3FileList.add(bookParams.getSOURCE_VOICE_FOLDER() + bookEntity.getChId() + ".mp3");
                    }
                }
            }
            // 目标单页音频
            String destEnPageMp3FilePath = bookParams.getDEST_AUDIO_AUDIOBYPAGE_FOLDER() + pageNum + ".mp3";
            String destChPageMp3FilePath = bookParams.getDEST_AUDIO_AUDIOBYPAGE_CH_FOLDER() + pageNum + ".mp3";
            if (!bookParams.isCh()) {
                Utils.combinMp3File(sourceEnMp3FileList, destEnPageMp3FilePath);
            }
            Utils.combinMp3File(sourceChMp3FileList, destChPageMp3FilePath);
            // 目标单页文本
            String destEnPageTextFilePath = bookParams.getDEST_TEXT_TEXTBYPAGE_FOLDER() + pageNum + ".txt";
            String destChPageTextFilePath = bookParams.getDEST_TEXT_TEXTBYPAGE_CH_FOLDER() + pageNum + ".txt";
            if (!bookParams.isCh()) {
                Utils.writeToTxt(destEnPageTextFilePath, sbEn.toString().trim());
            }
            Utils.writeToTxt(destChPageTextFilePath, sbCh.toString().trim());
        }
    }

    /**
     * 将一本书，按域生成音频
     *
     * @param bookEntityMap 整本书的内容
     * @param bookParams    书文件夹名称
     */
    public static void combinVoiceTextBySegment(Map<String, List<BookAreaEntity>> bookEntityMap, BookParams bookParams) {
        System.out.println(String.format("[%s][按域合成音频+文字]", bookParams.getBookName()));
        int index = -1;
        // 按页遍历
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
            int imgX = 0;
            int imgY = 0;
            try {
                FileInputStream in = new FileInputStream(new File(imgFilePath));
                BufferedImage sourceImg = ImageIO.read(in);
                if (sourceImg != null) {
                    imgX = sourceImg.getWidth();
                    imgY = sourceImg.getHeight();
                } else {
                    System.err.println("读取图片出错： " + imgFilePath);
                }
            } catch (Exception e) {
                System.err.println("读取图片出错： " + imgFilePath);
                e.printStackTrace();
            }
            // 当前页的域（excel中的行）
            List<BookAreaEntity> bookEntities = bookEntityMap.get(page);
            // 根据每一页的框号，找到对应的矩形坐标，生成MP3文件名
            // bookEntity 为excel中的一行
            for (BookAreaEntity bookEntity : bookEntities) {
                // 英文音频
                List<String> sourceMp3ENList = new ArrayList<>();
                // 中文音频
                List<String> sourceMp3CHList = new ArrayList<>();
                // 英文音频源文件集合
                if (CollectionUtils.isNotEmpty(bookEntity.getEnIDs())) {
                    for (String enId : bookEntity.getEnIDs()) {
                        if (StringUtils.isBlank(enId)) {
                            System.err.println("英文音频ID空" + bookEntity.toString());
                            continue;
                        }
                        // 当前域对应的mp3文件
                        String sourceMp3FilePath = bookParams.getSOURCE_VOICE_FOLDER() + enId + ".mp3";
                        sourceMp3ENList.add(sourceMp3FilePath);
                    }
                } else {
                    if (StringUtils.isBlank(bookEntity.getEnId())) {
                        System.err.println("英文音频ID空" + bookEntity.toString());
                    } else {
                        // 当前域对应的英文mp3文件
                        String sourceMp3FilePath = bookParams.getSOURCE_VOICE_FOLDER() + bookEntity.getEnId() + ".mp3";
                        sourceMp3ENList.add(sourceMp3FilePath);
                    }
                }

                // 中文音频源文件集合
                if (CollectionUtils.isNotEmpty(bookEntity.getChIDs())) {
                    for (String chId : bookEntity.getChIDs()) {
                        if (StringUtils.isBlank(chId)) {
                            continue;
                        }
                        // 当前域对应的中文mp3文件
                        String sourceMp3FilePath = bookParams.getSOURCE_VOICE_FOLDER() + chId + ".mp3";
                        sourceMp3CHList.add(sourceMp3FilePath);
                    }
                } else {
                    // 当前域对应的中文mp3文件
                    if (StringUtils.isNotBlank(bookEntity.getChId())) {
                        String sourceMp3FilePath = bookParams.getSOURCE_VOICE_FOLDER() + bookEntity.getChId() + ".mp3";
                        sourceMp3CHList.add(sourceMp3FilePath);
                    }
                }
                // asm文件中，一个框号，可能对应多个域（相当于excel中的行）——框号，对应多个音频文件——音频内容相同，域坐标不同）
                int kuangNum = Integer.valueOf(bookEntity.getAreaId());
                List<AsmEntity> asmEntities = asmMap.get(kuangNum);
                if (CollectionUtils.isEmpty(asmEntities)) {
                    System.err.println("根据excel框号读取asm信息为空：" + kuangNum + bookParams.getBookName() + bookEntity);
                }
                for (AsmEntity asmEntity : asmEntities) {
                    index++;
                    String name = String.format("%d-%s-%s-%s-%s-%d"
                            , pageNum
                            , NumberUtil.roundStr((double) CoordinateConstant.getCoordinateValue(asmEntity.getTopLeftX(), type) / (double) imgX, 6)
                            , NumberUtil.roundStr((double) CoordinateConstant.getCoordinateValue(asmEntity.getTopLeftY(), type) / (double) imgY, 6)
                            , NumberUtil.roundStr((double) CoordinateConstant.getCoordinateValue(asmEntity.getLowerRightX(), type) / (double) imgX, 6)
                            , NumberUtil.roundStr((double) CoordinateConstant.getCoordinateValue(asmEntity.getLowerRightY(), type) / (double) imgY, 6)
                            , index);
                    // 音频
                    String destENMp3FileName = bookParams.getDEST_AUDIO_AUDIOSEGMENTS_FOLDER() + name + ".mp3";
                    String destCHMp3FileName = bookParams.getDEST_AUDIO_AUDIOSEGMENTS_CH_FOLDER() + name + ".mp3";
                    if (!bookParams.isCh()) {
                        Utils.combinMp3File(sourceMp3ENList, destENMp3FileName);
                    }
                    Utils.combinMp3File(sourceMp3CHList, destCHMp3FileName);

                    // 文本,一个音频对应一个文本
                    String destENTextsegmentsFilePath = bookParams.getDEST_TEXT_TEXTSEGMENTS_FOLDER() + pageNum + "-" + index + ".txt";
                    if (!bookParams.isCh()) {
                        Utils.writeToTxt(destENTextsegmentsFilePath, bookEntity.getEnContent().trim());
                    }
                    String destCHTextsegmentsFilePath = bookParams.getDEST_TEXT_TEXTSEGMENTS_CH_FOLDER() + pageNum + "-" + index + ".txt";
                    Utils.writeToTxt(destCHTextsegmentsFilePath, bookEntity.getChContent().trim());
                }
            }
        }
    }
}
