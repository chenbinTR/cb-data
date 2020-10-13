package book.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangsx
 * @Title: book.entity.BookParams
 * @Description: TODO
 * @date 2020/3/31
 */
@Getter
@Setter
@NoArgsConstructor
public class BookParams {
    /**
     * 根目录
     */
    private String root = "E:\\BOOK_DATA\\已处理\\已修正-加静音段\\";
//    private String root = "E:\\BOOK_DATA\\已处理\\已修正\\";
    private String SOURCE_COORDIATE_FILE = "E:\\BOOK_DATA\\已s处理\\DEFINE-32.H";
    private String SOURCE_COORDIATE_NEW_FILE = "E:\\BOOK_DATA\\已处理\\标准框2-DEFINE-00.H";
    private boolean isCh;

    public BookParams(String seriesName, String bookName, boolean isCh) {
        this.isCh = isCh;
        this.seriesName = seriesName;
        this.bookName = bookName;

        // source path
        String SOURCE_ROOT_FOLDER = root + seriesName + File.separator + bookName;

        SOURCE_BOOK_FOLDER = SOURCE_ROOT_FOLDER + File.separator;
        SOURCE_IMG_FOLDER = SOURCE_ROOT_FOLDER + "\\dbmp\\";
        SOURCE_ASM_FOLDER = SOURCE_ROOT_FOLDER + "\\coordsrc\\";
        SOURCE_VOICE_FOLDER = SOURCE_ROOT_FOLDER + "\\语音\\";

        // dest path
        String DEST_ROOT_FOLDER = root + seriesName + "-result\\" + bookName;
        // 英文
        DEST_BOOK_FOLDER = DEST_ROOT_FOLDER + "\\";
        DEST_AUDIO_FOLDER = DEST_ROOT_FOLDER + "\\Audio_1\\";
        DEST_AUDIO_AUDIOBYPAGE_FOLDER =  DEST_AUDIO_FOLDER + "AudioByPage\\";
        DEST_AUDIO_AUDIOSEGMENTS_FOLDER = DEST_AUDIO_FOLDER + "AudioSegments\\";
        DEST_BOOKIMG_FOLDER = DEST_ROOT_FOLDER + "\\Bookimg\\";
        DEST_BOOKING_MERGE_FOLDER = DEST_ROOT_FOLDER + "\\Bookimg\\Merge\\";
        DEST_TEXT__FOLDER = DEST_ROOT_FOLDER + "\\text\\";
        DEST_TEXT_TEXTBYPAGE_FOLDER = DEST_ROOT_FOLDER + "\\text\\textByPage\\";
        DEST_TEXT_TEXTSEGMENTS_FOLDER = DEST_ROOT_FOLDER + "\\text\\TextSegments\\";

        // 中文
        DEST_AUDIO_AUDIOBYPAGE_CH_FOLDER = DEST_AUDIO_FOLDER + "AudioByPageCH\\";
        DEST_AUDIO_AUDIOSEGMENTS_CH_FOLDER = DEST_AUDIO_FOLDER + "AudioSegmentsCH\\";
        DEST_TEXT_TEXTBYPAGE_CH_FOLDER = DEST_ROOT_FOLDER + "\\text\\textByPageCH\\";
        DEST_TEXT_TEXTSEGMENTS_CH_FOLDER = DEST_ROOT_FOLDER + "\\text\\TextSegmentsCH\\";

        initFolder();
    }

    /**
     * 获取套书文件夹下的所有书名
     * @param bookFolderName
     * @return
     */
    public List<String> getBookNameList(String bookFolderName){
        File file = new File(root + bookFolderName);
        return Arrays.asList(file.list());
    }
    private void initFolder(){
        mkdirs(DEST_AUDIO_AUDIOBYPAGE_FOLDER);
        mkdirs(DEST_AUDIO_AUDIOSEGMENTS_FOLDER);
        mkdirs(DEST_BOOKING_MERGE_FOLDER);
        mkdirs(DEST_TEXT_TEXTBYPAGE_FOLDER);
        mkdirs(DEST_TEXT_TEXTSEGMENTS_FOLDER);

        mkdirs(DEST_AUDIO_AUDIOBYPAGE_CH_FOLDER);
        mkdirs(DEST_AUDIO_AUDIOSEGMENTS_CH_FOLDER);
        mkdirs(DEST_TEXT_TEXTBYPAGE_CH_FOLDER);
        mkdirs(DEST_TEXT_TEXTSEGMENTS_CH_FOLDER);
    }
    private void mkdirs(String path){
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 一套书的文件夹名字
     */
    private String seriesName;
    /**
     * 单本书的文件夹名字
     */
    private String bookName;
    /**
     * 源文件路径
     */
    private String SOURCE_BOOK_FOLDER;
    private String SOURCE_IMG_FOLDER;
    private String SOURCE_ASM_FOLDER;
    private String SOURCE_VOICE_FOLDER;

    /**
     * 目标文件路径
     */
    private String DEST_BOOK_FOLDER;
    private String DEST_AUDIO_FOLDER;
    private String DEST_AUDIO_AUDIOBYPAGE_FOLDER;
    private String DEST_AUDIO_AUDIOSEGMENTS_FOLDER;
    private String DEST_BOOKIMG_FOLDER;
    private String DEST_BOOKING_MERGE_FOLDER;
    private String DEST_TEXT__FOLDER;
    private String DEST_TEXT_TEXTBYPAGE_FOLDER;
    private String DEST_TEXT_TEXTSEGMENTS_FOLDER;
    /**
     * 中文内容文件夹
     */
    private String DEST_AUDIO_AUDIOBYPAGE_CH_FOLDER;
    private String DEST_AUDIO_AUDIOSEGMENTS_CH_FOLDER;
    private String DEST_TEXT_TEXTBYPAGE_CH_FOLDER;
    private String DEST_TEXT_TEXTSEGMENTS_CH_FOLDER;

}
