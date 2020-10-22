package book.spider;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import img.ImageRemarkUtil;
import img.ImgUtils;
import utils.FileHelper;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ChenOT
 * @Description: TODO
 * @date 2020/10/13
 */
public class Spider1 {

    private static Map<String, String> grade_MAP;

    static {
        grade_MAP = new HashMap<>();
        grade_MAP.put("一年级上", "2269");
        grade_MAP.put("一年级下", "2568");
        grade_MAP.put("二年级上", "2574");
        grade_MAP.put("二年级下", "2576");
        grade_MAP.put("三年级上", "2582");
        grade_MAP.put("三年级下", "2584");
        grade_MAP.put("四年级上", "2588");
        grade_MAP.put("四年级下", "2591");
        grade_MAP.put("五年级上", "2594");
        grade_MAP.put("五年级下", "2597");
        grade_MAP.put("六年级上", "2600");
        grade_MAP.put("六年级下", "2603");
    }

    public static void main(String[] args) {
        ttt();
    }
    private static void ttt(){
        List<String> contents = Utils.readFileToList("E:\\new 1");
        for (String content : contents) {
            String[] items = content.split("\t");
            FileHelper.downloadFile(items[0], new File("E:\\"+items[1].replace(" ","").trim()+".mp3"));
        }
    }
    private static void combinJpg() {
        grade_MAP.forEach((k, v) -> {
            String path = "E:\\西南师大版小学数学\\" + k + "\\";
            String pathNew = "E:\\西南师大版小学数学_combin\\" + k + "\\";
            if (!FileUtil.exist(pathNew)) {
                FileUtil.mkdir(pathNew);
            }
            String[] jpgs = new File(path).list();
            List<String> fileNames = Arrays.asList(jpgs);
            List<String> fileNamesNew = fileNames.stream()
                    .sorted(Comparator.comparing(e1 -> Integer.valueOf(e1.replace(".jpg", ""))))
                    .collect(Collectors.toList());
            String last = null;
            int index = 1;
            for (int i = 0; i < fileNamesNew.size(); i++) {
                String fileName = fileNamesNew.get(i);
                if (i == 0) {
                    // 第一个
                    FileUtil.copy(path + fileName, pathNew + index + ".jpg", true);
                    index++;
                } else if (i == fileNamesNew.size() - 1) {
                    // 最后一个
                    if (Integer.valueOf(last.replace(".jpg", "")) % 2 == 0) {
                        try {
                            ImgUtils.mergeImage(true, pathNew + index + ".jpg", ImgUtils.loadImageLocal(path + last), ImgUtils.loadImageLocal(path + fileName));
                        } catch (IOException e) {
                            System.err.println(last + "\t" + fileName);
                            e.printStackTrace();
                        }
                        index++;
                    } else {
                        FileUtil.copy(path + fileName, pathNew + index + ".jpg", true);
                        index++;
                    }
                } else {
                    if (Integer.valueOf(last.replace(".jpg", "")) % 2 == 0) {
                        try {
                            ImgUtils.mergeImage(true, pathNew + index + ".jpg", ImgUtils.loadImageLocal(path + last), ImgUtils.loadImageLocal(path + fileName));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        index++;
                    }
                }
                last = fileName;
            }
        });
    }

    /**
     * http://www.1010jiajiao.com
     */
    private static void spiderBook() {
        grade_MAP.forEach((k, v) -> {
            String url = "http://thumb.1010pic.com/dmt/diandu5/file/book/";
            String path = "E:\\西南师大版小学数学\\" + k + "\\";
            if (!FileUtil.exist(path)) {
                FileUtil.mkdir(path);
            }
            int index = 1;
            while (true) {
                String downloadUrl = url + v + "/" + index + ".jpg";
                File file = new File(path + index + ".jpg");
                boolean b = FileHelper.downloadFile(downloadUrl, file);
                if (!b) {
                    file.delete();
                    break;
                }
                index++;
            }
        });
    }
}
