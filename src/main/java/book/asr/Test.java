package book.asr;

import org.apache.commons.codec.binary.Base64;
import utils.Utils;

import java.io.IOException;

/**
 * @author ChenOT
 * @date 2020-01-03
 * @see
 * @since
 */
public class Test {
    public static void main(String[] args) throws IOException {
        //读取音频文件
//        byte[] data = Utils.toByteArray2("Q:\\1532570026270.wav");
//        Base64 base64 = new Base64();
//        String speech = base64.encodeToString(data);
//        System.out.println(speech);

        try {
//            Runtime.getRuntime().exec("ffmpeg -y -f s16le -ar 8000 -ac 2 -i Q:\\20180523204926_3319.pcm -acodec libmp3lame Q:\\xixi.mp3");
//            Runtime.getRuntime().exec("ffmpeg -y -f s16le -ar 16000 -ac 1 -i Q:\\14016-10.mp3 -acodec pcm_s16le Q:\\14016-10.pcm");
            Runtime.getRuntime().exec("ffmpeg -i Q:\\14016-10.mp3 -f s16be -ar 16000 -ac 1 -acodec pcm_s16be Q:\\14016-10.pcm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
