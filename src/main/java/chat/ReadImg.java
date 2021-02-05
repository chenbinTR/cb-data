package chat;

import utils.Utils;
import utils.UtilsMini;

import javax.swing.*;
import java.io.File;

/**
 * @author ChenOT
 * @Description: TODO
 * @date 2021/2/5
 */
public class ReadImg {
    public static void main(String[] args) {
        String root = "/data/work/img/";
        File file = new File(root);
        for (String imgName : file.list()) {
            try {
                String imgPath = root + imgName;
                String result = readImgWH(imgPath);
                UtilsMini.writeToTxt("/data/work/img_height_java.txt", imgName + "\t" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String readImgWH(String imgPath) {
        ImageIcon imageIcon = new ImageIcon(imgPath);
        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();
        return iconWidth + "-" + iconHeight;
    }
}
