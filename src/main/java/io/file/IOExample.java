package io.file;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * @author ChenOT
 * @date 2019-10-29
 * @see
 * @since
 */
public class IOExample {
    public static void main(String[] args) {
//        method1();
        for(int i=0; i<10; i++){
            long time1 = System.currentTimeMillis();
            method2();
            System.out.println(System.currentTimeMillis() - time1);
        }
    }

    /**
     * 以字节流的方式读文件
     */
    public static void method1() {
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream("Q:\\1.txt"));
            byte[] buf = new byte[1024];
            int bytesRead = in.read(buf);
            while (bytesRead != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    System.out.println((char) buf[i]);
                }
                bytesRead = in.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 以字符流的方式，读取
     */
    private static void method2() {
        BufferedReader bufferedReader = null;
        try {
            // 读取文件字节流
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\cb\\Downloads\\hanlp\\data\\dictionary\\CoreNatureDictionary.ngram.txt");
            // 通过inputstreamreader将字节流转换为字符流
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            // 创建bufferedR
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while (StringUtils.isNotBlank(line = bufferedReader.readLine())) {
//                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
