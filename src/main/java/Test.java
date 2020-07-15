import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import utils.SymmetricEncoder;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ChenOT
 * @date 2019-06-20
 * @see
 * @since
 */
public class Test {
    /**
     * cdn能力所限，只能单线程
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    private static void getImgInfo() {
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
    private static void processLog() {
        LineIterator lineIterator = null;
        try {
            lineIterator = FileUtils.lineIterator(new File("Q:\\logs\\202006.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        LineIterator lineIteratorQ = FileUtils.lineIterator(new File("Q:\\humor_high_dialogue20180708_2020-05-21.txt"));
        int count = 0;
        while (lineIterator.hasNext()) {
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
                if (parsetype.equals("50")) {
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

    static {

        try {
            MEMBER_CREATE_DATE = simFormat.parse("2020-04-21 22:00:00");
            createDate = simFormat.parse("2020-04-21 22:00:01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        File file = new File("Q:\\logs\\wiki.txt");
//        file.deleteOnExit();
//        System.out.println("123".substring(1,0));
//        List<String> contents = Utils.readFileToList("Q:\\logs\\dict_synonym.txt");
//        String question1 = "%s的近义词是什么";
//        String question2 = "%s的近义词";
//        String answer = "%s的近义词有：%s";
//        for (String content : contents) {
//            content = content.substring(9).trim();
//            System.out.println(content);
//            String[] words = content.split("\\s");
//            if(words.length<2){
//                continue;
//            }
//            System.out.println(words.length);
//            List<String> wordList = Arrays.asList(words);
//            for(String word:wordList){
//                List<String> wordListCopy = new ArrayList<>(wordList.size());
//                wordListCopy.addAll(wordList);
//                wordListCopy.remove(word);
//                String q1 = String.format(question1, word);
//                String q2 = String.format(question2, word);
//                String a = String.format(answer, word, StringUtils.join(wordListCopy.toArray(), "，"));
//                Utils.writeToTxt("Q:\\logs\\dict_synonym_qa.txt", q1 + "|" + q2 + "\t" + a);
//            }
//        }
    }

    private static void tt(){
        List<String> contents = Utils.readFileToList("Q:\\logs\\dict_antonym.txt");
        String question1 = "%s的反义词是什么";
        String question2 = "%s的反义词";
        String answer = "%s的反义词是：%s";
        for (String content : contents) {
            String[] words = content.split("-");
            if (words.length != 2) {
                System.err.println(content);
            }
            String waq1 = String.format(question1, words[0]);
            String waq2 = String.format(question2, words[0]);
            String waa1 = String.format(answer, words[0], words[1]);

            String wbq1 = String.format(question1, words[1]);
            String wbq2 = String.format(question2, words[1]);
            String wba1 = String.format(answer, words[1], words[0]);


            Utils.writeToTxt("Q:\\logs\\dict_antonym_qa.txt", waq1 + "|" + waq2 + "\t" + waa1);
            Utils.writeToTxt("Q:\\logs\\dict_antonym_qa.txt", wbq1 + "|" + wbq2 + "\t" + wba1);
        }
    }
}
