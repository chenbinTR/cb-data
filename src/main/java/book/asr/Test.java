package book.asr;

import org.apache.commons.codec.binary.Base64;
import utils.Utils;

import java.io.IOException;
import java.util.List;

/**
 * @author ChenOT
 * @date 2020-01-03
 * @see
 * @since
 */
public class Test {
    public static void main(String[] args) throws IOException {
        String cmd = "ffmpeg -i E:\\07_Iam_fire.mp3 -vn -acodec copy -ss %s -t %s E:\\07_Iam_fire\\%s.mp3";
        //读取音频文件
//        byte[] data = Utils.toByteArray2("Q:\\1532570026270.wav");
//        Base64 base64 = new Base64();
//        String speech = base64.encodeToString(data);
//        System.out.println(speech);
        List<String> contents = Utils.readFileToList("E:\\Iam_fire.txt");
        for (String content : contents) {
            String[] times = content.substring(content.indexOf("=")+1).split("_");
            String page = content.substring(0, content.indexOf("="));
            if(null == times || times.length !=2){
                continue;
            }
            try {
                Runtime.getRuntime().exec(String.format(cmd, times[0], times[1], page));
//            Runtime.getRuntime().exec("ffmpeg -y -ac 1 -ar 16000 -f s16le -i Q:\\turing-tts\\speex\\apple_5.pcm -acodec libmp3lame Q:\\turing-tts\\speex\\apple_5.mp3");
//            Runtime.getRuntime().exec("ffmpeg -y -f s16be -ac 1 -ar 16000 -acodec pcm_s16le -i Q:\\turing-tts\\speex\\tts-100ca903d1fd437b98c3695fccd4f302-5cced5836e054b0c90c6cc9901c4045a.pcm Q:\\turing-tts\\speex\\tts-100ca903d1fd437b98c3695fccd4f302-5cced5836e054b0c90c6cc9901c4045a.mp3");
//            Runtime.getRuntime().exec("ffmpeg -i Q:\\14016-10.mp3 -f s16be -ar 16000 -ac 1 -acodec pcm_s16be Q:\\14016-10.pcm");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
