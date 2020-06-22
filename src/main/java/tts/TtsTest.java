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
     *
     */
    private final String TTS_URL = "";
    private TtsRequest ttsRequest;
    @Before
    public void initData(){
        ttsRequest = new TtsRequest();
        ttsRequest.setText_str("哼唱的声音");
        ttsRequest.setBiz_code("e2e_xixi_16k");
        ttsRequest.setEncode_fmt("base64");
        ttsRequest.setStream(0);
        ttsRequest.setSpeed(9);
        ttsRequest.setLoudness(5);
        ttsRequest.setArousal(6);
//        System.out.println(JSONObject.toJSONString(ttsRequest));
    }
    @Test
    public void testPcmRateTrans(){
        Set<String> d = new HashSet<>();
        // 采样率转换
        try {
//            Runtime.getRuntime().exec("ffmpeg -i Q:\\66be849e-cead-4f20-a84a-2d990bb47e24_dest.pcm -f s16le -acodec -ar 8000 -ac 1 Q:\\pcm8k.pcm");
            Runtime.getRuntime().exec("ffmpeg -y -i Q:\\25.mp3 -libmp3lame -ac 1 Q:\\27.mp3");
//            Runtime.getRuntime().exec("ffmpeg -y -i Q:\\1.pcm -f s16be -ar 8000 -ac 1 -acodec pcm_s16be Q:\\2.pcm");
//            Runtime.getRuntime().exec("ffmpeg -i \"concat:Q:\\6.mp3|Q:\\6.mp3|Q:\\6.mp3\" -acodec copy Q:\\output.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testTuringTTS(){
//        String param = "{\"text_str\":\"我是张清一\", \"encode_fmt\":\"base64\", \"biz_code\":\"e2e_xixi_16k\", \"stream\":0}";
//        String param = "{\"syl_flag\": 0,\"stream\": 0,\"speed\": 5,\"fast_mode\":0,\"track_key\": \"\", \"text_str\": \"的了还叶比人，家国展王想长坏蜀。\", \"loudness\": 1, \"biz_code\": \"e2e_xixi_16k\", \"encode_fmt\": \"base64\"}";
//        String param = "{\"arousal\":1.2,\"biz_code\":\"e2e_xixi_16k\",\"encode_fmt\":\"base64\",\"f0_bias\":1.0,\"fast_mode\":1,\"globalId\":\"111749186808810041\",\"loudness\":1.0,\"speed\":0.9,\"stream\":0,\"text_str\":\"哼唱的声音\"}";
        String result = Utils.httpPost(JSON.toJSONString(ttsRequest), TTS_URL);
        String[] results = result.split("}\\{");
        final String pcmData = JSON.parseObject(results[0]+"}").getString("data");
        // 写入pcm文件
        base64ToFile("Q:\\",pcmData, "xixi.pcm");
        // 转wav
//        byte[] bytes = Base64.getDecoder().decode(pcmData);
//        System.out.println(bytes.length);
//        byte[] wavHeader = TTSUtil.getWavHeader(bytes.length);
//        byte[] wavBytes =  TTSUtil.byteMerger(wavHeader, bytes);
//        bytesToFile("Q:\\", wavBytes, "xixi.wav");
        // 转mp3
        try {
            Runtime.getRuntime().exec("ffmpeg -y -f s16le -ar 8000 -ac 2 -i Q:\\xixi.pcm -acodec libmp3lame Q:\\xixi.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * base64 写入文件
     * @param destPath
     * @param fileName
     */
    private void bytesToFile(String destPath,byte[] bytes, String fileName) {
        File file;
        //创建文件目录
        String filePath=destPath;
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            file=new File(filePath+"/"+fileName);
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
     * @param destPath
     * @param base64
     * @param fileName
     */
    private void base64ToFile(String destPath,String base64, String fileName) {
        File file;
        //创建文件目录
        String filePath=destPath;
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file=new File(filePath+"/"+fileName);
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
