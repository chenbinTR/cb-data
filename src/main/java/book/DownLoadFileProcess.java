package book;

import utils.FileHelper;
import utils.UtilsMini;

import java.io.File;
import java.util.List;

/**
 * @author ChenOT
 * @date 2020-03-24
 * @see
 */
public class DownLoadFileProcess {
    private static String path = "E:\\BOOK_DATA\\刷新数据\\";
    public static void main(String[] args) {

        File urlFolder = new File(path+"url");
        String[] urlFiles = urlFolder.list();
        for(String urlFile:urlFiles){
            String bookName = urlFile.replace(".txt","");
            System.out.println(bookName);
            processOneBook(bookName);
        }
    }

    /**
     * 下载一本书所有音频
     */
    private static void processOneBook(String bookName){
        String bookPath = path+bookName+File.separator;
        new File(bookPath).mkdirs();
        List<String> urls = UtilsMini.readFileToList(path+"url"+File.separator + bookName + ".txt");
        for (String url : urls) {
            try {
                String fileName = url.trim().substring(url.lastIndexOf("/") + 1);
                String filePath = bookPath + fileName;
                if (!FileHelper.downloadFile(url.trim(), new File(filePath))) {
                    UtilsMini.writeToTxt(bookPath + "error.txt", url);
                }
            } catch (Exception e) {
                UtilsMini.writeToTxt(bookPath + "error.txt", url);
            }
        }
    }
}
