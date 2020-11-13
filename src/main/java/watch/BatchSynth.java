package watch;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.dictionary.py.String2PinyinConverter;
import org.apache.commons.lang3.StringUtils;
import utils.FileHelper;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 智娃tts音频生成
 *
 * @author ChenOT
 * @date 2019-08-13
 * @see
 */
public class BatchSynth {
    private static final String path = "E:\\idiom\\";
    private static final String END_SYMBOL = "。";
    private static final String COMMA_SYMBOL = "，";

    public static void main(String[] args) throws IOException {
        batchProcess();
    }

    /**
     * 批量tts合成
     */
    private static void batchProcess() {
        // 读取待处理的源数据
        List<String> lines = FileHelper.readFileToList("E:\\2.txt");
        for (String line : lines) {
            String[] items = line.split("\t");
            String idiom = items[0];
            String explain = items[1];
            // 先按照句号拆分
            String[] explainItems = explain.split(END_SYMBOL);
            List<String> explainList = new ArrayList<>(explainItems.length);
            for (String explainItem : explainItems) {
                explainItem = explainItem.trim() + END_SYMBOL;
                // 再按照逗号拆分
                String[] commaItems = explainItem.split(COMMA_SYMBOL);
                for (String commaItem : commaItems) {
                    if (!commaItem.endsWith(END_SYMBOL)) {
                        commaItem = commaItem.trim() + COMMA_SYMBOL;
                    }
                    if(Utils.isContainChinese(commaItem)){
                        explainList.add(commaItem);
                    }
                }
            }
            // 把拆分好的断句分别tts生成音频，再合成一个音频
            if (!batchTts(explainList, idiom)) {
                FileHelper.writeToTxt(path + "wrong.txt", line);
            }
        }
    }

    /**
     * 批量处理tts合成，并将装换的多个音频文件合成一个
     *
     * @param contents 待tts转换的内容集
     * @param fileName 内容集处理后的多个音频文件合成为一个之后的文件名
     */
    private static boolean batchTts(List<String> contents, String fileName) {
        List<String> mp3Files = new ArrayList<>(contents.size());
        boolean isCorrect = true;
        for (String content : contents) {
            String mp3Url = executeAIWIFI(content);
            if (StringUtils.isBlank(mp3Url)) {
                isCorrect = false;
                break;
            }
            String mp3File = String.format(path + "%s.mp3", UUID.randomUUID().toString());
            File file = new File(mp3File);
            if (!FileHelper.downloadFile(mp3Url, file)) {
                isCorrect = false;
                break;
            }
            mp3Files.add(mp3File);
        }
        //tts转换成功，合并成一个音频文件

        if (isCorrect) {
            String pinyin = org.apache.commons.lang.StringUtils.join(String2PinyinConverter.convert(fileName).toArray());
            FileHelper.combinFile(mp3Files, path + pinyin + ".mp3");
            Utils.writeToTxt("E:\\tt.txt", fileName, pinyin);
        }
        for (String mp3File : mp3Files) {
            FileUtil.del(mp3File);
        }
        return isCorrect;
    }

    /**
     * 测试单句tts生成
     */
    private static void testTts() {
        String content = "小朋友,听智娃给你讲一讲成语画龙点睛的故事吧。南朝梁代著名书画家张僧繇特别擅长画龙，梁武帝在金陵建安乐寺，让张僧繇在墙上画龙，他画了四条龙栩栩如生，但都没有点眼睛。";
        String mp3Url = executeAIWIFI(content);
        if (StringUtils.isBlank(mp3Url)) {
            return;
        }
        String mp3File = String.format(path + "%s.mp3", "test4");
        File file = new File(mp3File);
        if (!FileHelper.downloadFile(mp3Url, file)) {
        }
    }

    /**
     * 调用aiwifi接口合成tts
     *
     * @param text 待合成的文本
     * @return 音频文件链接
     */
    private static String executeAIWIFI(String text) {
        TTSParameters ttsParameters = new TTSParameters(text);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("parameters", ttsParameters);
        try {
            String result = FileHelper.httpPost(jsonObject.toJSONString(), "http://aiwifi.alpha.tuling123.com/speech/v2/tts");
            JSONObject jsonResult = JSON.parseObject(result);
            if (jsonResult.getInteger("code") == 200) {
                return jsonResult.getJSONArray("url").getString(0);
            } else {
                System.err.println(text);
                System.err.println(jsonResult);
            }
        } catch (Exception e) {
            System.err.println(text);
            e.printStackTrace();
            return null;
        }
        return null;
    }


}
