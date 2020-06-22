package tts;

import java.io.IOException;

/**
 * @author ChenOT
 * @date 2019-09-20
 * @see
 * @since
 */
public class TTSUtil {
    public static void main(String[] args) {
        for(int i=0; i<100; i++){
            long time1 = System.currentTimeMillis();
            getWavHeader(385623856);
            System.out.println(System.currentTimeMillis() - time1);
        }
    }
    public static byte[] getWavHeader(int length) {
        // fileLength 录音数据的长度

        WaveHeader header = new WaveHeader(length);

// 返回44个字节的数组

        try {
            byte[] waveHeaderBytes = header.getHeader();
            return waveHeaderBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] byteMerger(byte[] bt1, byte[] bt2) {
        byte[] bt3 = new byte[bt1.length + bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }


}
