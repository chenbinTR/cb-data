package utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileHelper {
    public static void main(String[] args) throws IOException {
        File file = new File("Q:\\1.png");
        String downloadUrl = "https://turing-platform-openapi.oss-cn-beijing.aliyuncs.com/openapi/image/34e86b172bb145cbbbfd72395e58722f_34e86b172bb145cbbbfd72395e58722f_5c3f7b14405a49d8823d943bc5ce4ed6.png";
        downloadFile(downloadUrl, file);
    }
    public static boolean downloadFile(String downloadUrl, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            URL url = new URL(downloadUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, length);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断要下载的文件是否存在
     * @param downloadUrl
     * @return
     */
    public static boolean isFileExist(String downloadUrl) {
        try {
            URL url = new URL(downloadUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = new byte[1024];
            if (inputStream.read(bytes) == -1) {
                return false;
            }
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 读文件(按行读取，保存到list中，读取文件全部内容)
     *
     * @param filePath 文件路径
     */
    public static List<String> readFileToList(String filePath) {
        List<String> list = new ArrayList<String>();
        File file = new File(filePath);
        readFile(file, list);
        return list;
    }
    private static void readFile(File file, Collection collection) {
        BufferedReader reader = null;
        if (file.exists()) {
            try {
                reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), "UTF-8"));
                String temp = null;
                while (null != (temp = reader.readLine())) {
                    collection.add(temp.trim());
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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
    public static String httpPost(String param, String url) {
        return httpPost(param, url, "UTF-8");
    }

    /**
     * http post
     *
     * @param param
     * @param url
     * @return
     */
    public static String httpPost(String param, String url, String encode) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            out = new OutputStreamWriter(conn.getOutputStream(), encode);
            out.write(param);

            out.flush();
            out.close();
            //
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), encode));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}