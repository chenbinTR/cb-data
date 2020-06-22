import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ChenOT
 * @date 2019-06-20
 * @see
 * @since
 */
public class Test {
    private static void getImgInfo(){
        File file = new File("C:\\Users\\cb\\Downloads\\tt\\12\\7\\C360_2013-05-06-00-25-17-363.jpg");
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth(); // 像素
        int height = bi.getHeight(); // 像素
        System.out.println("width=" + width + ",height=" + height + ".");
    }
//    private static ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static void processLog(){
        LineIterator lineIterator = null;
        try {
            lineIterator = FileUtils.lineIterator(new File("Q:\\logs\\202006.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        LineIterator lineIteratorQ = FileUtils.lineIterator(new File("Q:\\humor_high_dialogue20180708_2020-05-21.txt"));
        int count = 0;
        while(lineIterator.hasNext()) {
            try {
                String line = lineIterator.nextLine();
                String[] items = line.split("\t");
                String question = items[1].trim();
                String answer = items[2].trim();
                String appid = items[3];
                String parsetype = items[4];

//                if(StringUtils.isAnyBlank(question,appid,parsetype,answer)){
//                    continue;
//                }
//                if(!appid.equals("2300101") && !appid.equals("2300102")){
//                    continue;
//                }
                if(parsetype.equals("50")){
                    System.out.println(line);
//                    Utils.writeToTxt("Q:\\logs\\1.txt", line);
                }
//                    Utils.writeToTxt("Q:\\logs\\1.txt", line);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }
    /**
     * 促销活动起始时间，需要根据用户创建时间来生效
     */
    private static Date MEMBER_CREATE_DATE;
    private static Date createDate;
    private static SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static{

        try {
            MEMBER_CREATE_DATE = simFormat.parse("2020-04-21 22:00:00");
            createDate = simFormat.parse("2020-04-21 22:00:01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("1"+System.lineSeparator()+"2");
        System.out.println(SimpleDateFormat.getDateTimeInstance(2,2).format(new Date()));
//        System.out.println(simFormat.format(DateUtils.addDays(new Date(), 3)));
//        System.out.println(Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File("Q:\\1-0.672500-0.553474-0.765625-0.589383-11.mp3"))));
    }
}
