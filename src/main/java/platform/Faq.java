package platform;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cb
 * @date 2018-10-23 10:01
 */
public class Faq {
    public static void main(String[] args) {
        String pathSimi = "/mnt/work/snd-faq/chenbin/faq-simi/";
        String pathFaq = "/mnt/work/snd-faq/chenbin/faq-q/";
        File file = new File(pathSimi);
        String[] files = file.list();
        Map<String, String> faqMap = new HashMap<>();
        // 遍历faqsimi数据
        for (String f : files) {
            System.out.println(f);
            faqMap.clear();
            String fileName = f.substring(0, f.indexOf("."));
            String faqTable = fileName.replace("turing_faq_simi", "turing_faq");
            // 加载对应faq数据到map
            File fFaq = new File(pathFaq + faqTable + ".txt");
            LineIterator itFaq = null;
            try {
                itFaq = FileUtils.lineIterator(fFaq, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            String faqLine;
            while (itFaq.hasNext()) {
                faqLine = itFaq.nextLine();
                String[] faqs = faqLine.split("\t");
                faqMap.put(faqs[0], faqs[1]);
            }

            File fSimi = new File(pathSimi + f);
            try {
                LineIterator it = FileUtils.lineIterator(fSimi, "UTF-8");
                String line;
                while (it.hasNext()) {
                    try {
                        line = it.nextLine();
                        String[] simis = line.split("\t");
                        String questionId = simis[0];
                        String question = simis[1];
                        if(faqMap.containsKey(questionId)){
                            writeToTxt("/mnt/work/snd-faq/chenbin/faq-simi-result.txt", String.format("%s\t%s", faqMap.get(questionId),question));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeToTxt(String path, String content) {
        File file = new File(path);
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file, true);
            writer = new BufferedWriter(fw);
            writer.write(content);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
