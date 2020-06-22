package emotion;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import utils.Utils;

import java.io.File;
import java.io.IOException;

/**
 * @author ChenOT
 * @date 2019-08-29
 * @see
 * @since
 */
public class EmotionLog {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\cb\\Downloads\\server.log.20190902");
        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count = 0;
        while (it.hasNext()) {
            try{
                System.out.println(++count);
                String line = it.nextLine();
                String time = line.substring(0, "2019-09-05 20:30:56".length());

                int start = line.indexOf("request_time=") + "request_time=".length();
                String fenci = line.substring(start);
                fenci = fenci.replace("ms","");
                int reqtime = Double.valueOf(fenci).intValue();

                int startText = line.indexOf("texts=['")+"texts=['".length();
                int endText = line.indexOf("] id=");
                String text = line.substring(startText, endText);

                if(reqtime>50){
                    Utils.writeToTxt("Q:\\emotion_time_log_20190902.txt", time+"\t"+text+"\t"+reqtime);
                }
//
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        LineIterator.closeQuietly(it);
    }
}
