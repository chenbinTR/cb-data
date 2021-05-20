package tts;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import utils.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ChenOT
 * @date 2019-08-14
 * @see
 * @since
 */
public class TtsTest {
    /**
     * -y 覆盖输出文件
     * -i 设定输入流
     * -f 设定输出格式
     * -ar 设定采样率
     * -ac 设定声音的Channel数
     * -acodec 设定声音编解码器，未设定时则使用与输入流相同的编解码器
     */
    private final String TTS_URL = "http://tts-v1.alpha.tuling123.com/tts/getText";
    private final String TTS_URL_P = "http://47.94.53.111/tts-inner/getText?globalId=111749";
    private TtsRequest ttsRequest;

    @Before
    public void initData() {
        ttsRequest = new TtsRequest();
        ttsRequest.setText_str("小朋友,听智娃给你讲一讲成语夸父逐日的故事吧");
        ttsRequest.setBiz_code("ht_16k_j3");
        ttsRequest.setEncode_fmt("base64");
        ttsRequest.setStream(0);
        ttsRequest.setSyl_flag(1);
        ttsRequest.setSpeed(1);
//        ttsRequest.setSpeed(5);
//        ttsRequest.setLoudness(5);
//        ttsRequest.setArousal(6);
//        System.out.println(JSONObject.toJSONString(ttsRequest));
    }

    @Test
    public void testPcmRateTrans() {
        // 采样率转换
        try {
//            Runtime.getRuntime().exec("ffmpeg -i Q:\\66be849e-cead-4f20-a84a-2d990bb47e24_dest.pcm -f s16le -acodec -ar 8000 -ac 1 Q:\\pcm8k.pcm");
//            Runtime.getRuntime().exec("ffmpeg -y -i Q:\\25.mp3 -libmp3lame -ac 1 Q:\\27.mp3");
            Runtime.getRuntime().exec("ffmpeg -y -i E:\\1.opus -f s16be -ac 1 E:\\27.pcm");
//            Runtime.getRuntime().exec("ffmpeg -y -i Q:\\1.pcm -f s16be -ar 8000 -ac 1 -acodec pcm_s16be Q:\\2.pcm");
//            Runtime.getRuntime().exec("ffmpeg -i \"concat:Q:\\6.mp3|Q:\\6.mp3|Q:\\6.mp3\" -acodec copy Q:\\output.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTuringTTS() {
        String fileName = "4";
//        List<String> results = Utils.httpPostList(JSON.toJSONString(ttsRequest), TTS_URL_P);
//        String data = "";
//        for (String s : results) {
//            if (s.indexOf("pcm_string") > 0) {
//                data = s;
//                break;
//            }
//        }
        String data = "";
        final String pcmData = JSON.parseObject(data).getString("data");
        // 写入pcm文件
        base64ToFile("E:\\", pcmData, fileName + ".pcm");
        // 转wav
//        byte[] bytes = Base64.getDecoder().decode(pcmData);
//        System.out.println(bytes.length);
//        byte[] wavHeader = TTSUtil.getWavHeader(bytes.length);
//        byte[] wavBytes =  TTSUtil.byteMerger(wavHeader, bytes);
//        bytesToFile("Q:\\", wavBytes, "xixi.wav");
        // 转mp3
        try {
//            Runtime.getRuntime().exec("ffmpeg -y -f s16le -ar 16000 -ac 1 -i E:\\xixi.pcm -acodec libmp3lame E:\\xixi.mp3");
//            Runtime.getRuntime().exec("ffmpeg -y -ac 1 -ar 16000 -f s16le -i E:\\"+fileName+".pcm -c:a libmp3lame -b:a 16k E:\\"+fileName+".mp3");
            Runtime.getRuntime().exec("ffmpeg -y -ac 1 -ar 16000 -f s16le -i E:\\" + fileName + ".pcm -c:a libmp3lame -b:a 16k E:\\" + fileName + ".wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * base64 写入文件
     *
     * @param destPath
     * @param fileName
     */
    private void bytesToFile(String destPath, byte[] bytes, String fileName) {
        File file;
        //创建文件目录
        String filePath = destPath;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            file = new File(filePath + "/" + fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * base64 写入文件
     *
     * @param destPath
     * @param base64
     * @param fileName
     */
    private void base64ToFile(String destPath, String base64, String fileName) {
        File file;
        //创建文件目录
        String filePath = destPath;
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file = new File(filePath + "/" + fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
