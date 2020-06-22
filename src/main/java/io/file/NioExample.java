package io.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ChenOT
 * @date 2019-10-29
 * @see
 * @since
 */
public class NioExample {
    public static void main(String[] args) {
        try {
            for(int i=0; i<10; i++){
                long time1 = System.currentTimeMillis();
                mathod2();
                System.out.println(System.currentTimeMillis() - time1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void method1() {
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("src/nio.txt", "rw");
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            int bytesRead = fileChannel.read(buf);
            System.out.println(bytesRead);
            while (bytesRead != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }
                buf.compact();
                bytesRead = fileChannel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void mathod2() throws IOException {
        // 设置输入源 & 输出地 = 文件
        String infile = "C:\\Users\\cb\\Downloads\\hanlp\\data\\dictionary\\CoreNatureDictionary.ngram.txt";
//        String outfile = "C:\\copy.txt";

        // 1. 获取数据源 和 目标传输地的输入输出流（此处以数据源 = 文件为例）
        FileInputStream fin = new FileInputStream(infile);
//        FileOutputStream fout = new FileOutputStream(outfile);

        // 2. 获取数据源的输入输出通道
        FileChannel fcin = fin.getChannel();
//        FileChannel fcout = fout.getChannel();

        // 3. 创建缓冲区对象
        int length = 2048;
        ByteBuffer buff = ByteBuffer.allocate(length);

        while (true) {

            // 4. 从通道读取数据 & 写入到缓冲区
            // 注：若 以读取到该通道数据的末尾，则返回-1
            int r = fcin.read(buff);
            if (r == -1) {
                break;
            }
            // 5. 传出数据准备：调用flip()方法
//            buff.flip();

            // 6. 从 Buffer 中读取数据 & 传出数据到通道
//            fcout.write(buff);

            // 7. 重置缓冲区
            buff.clear();
//            System.out.write(buff.array(), 0, 1024);
//            System.out.println("-----------------");
        }
    }
}
