package python;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {
    private static ExecutorService cleanExector = Executors.newFixedThreadPool(5);
    private static volatile int count = 0;


    public static void main(String[] args) {
        String path = "E:\\mnt\\data\\universe-book-update\\img\\";
        File file = new File(path);
        final CountDownLatch countDownLatch = new CountDownLatch(file.list().length);
        for (String s : file.list()) {
            String imagePath = path + s;
            cleanExector.submit(() -> {
                try {
                    isEquals(imagePath);
                } catch (Exception e) {
//                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                    System.out.println(countDownLatch.getCount());
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("异常总量： " + count);
    }

    private static void isEquals(String imagePath) {
        String javaR = readImgWH(imagePath);
        String pythonR = readImgWHPython(imagePath);
        if (!javaR.equals(pythonR)) {
                count++;
                System.out.println(imagePath);
        }
    }

    private static String readImgWH(String imgPath) {
        ImageIcon imageIcon = new ImageIcon(imgPath);
        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();
        return iconWidth + "-" + iconHeight;
    }

    private static String readImgWHPython(String imgPath) {
        String result = "";
        try {
            String[] args = new String[]{"python", "E:\\git\\universe-book-update\\utils\\demo.py", imgPath};
            Process proc = Runtime.getRuntime().exec(args);// 执行py文件

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                result = line;
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void method1() {
        // TODO Auto-generated method stub
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python E:\\git\\universe-book-update\\utils\\demo.py");// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

