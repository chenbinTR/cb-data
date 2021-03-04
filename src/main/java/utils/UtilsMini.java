package utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by cb on 2017-09-29.
 */
public class UtilsMini {
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
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Auth-Token", "f8dfe5d353234c71ac9bafb410ae62de");
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
    /**
     * 替代replaceAll方法
     * @param pattern
     * @param originStr
     * @param replacement
     * @return
     */
    public static String patternReplace(Pattern pattern, String originStr, String  replacement){
        return pattern.matcher(originStr).replaceAll(replacement);
    }
    /**
     * 判断两个日期相差ms数
     *
     * @param startDate 起始日期：2019-09-09 09:23:12
     * @param endDate   结束日期：2019-09-09 09:23:12
     * @return
     */
    public static long getMsDiff(String startDate, String endDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateEnd = dateFormat.parse(endDate);
            if (null == startDate) {
                startDate = dateFormat.format(new Date());
            }
            Date dateNow = dateFormat.parse(startDate);

            long timeEnd = dateEnd.getTime();
            long timeStart = dateNow.getTime();

            long days = timeEnd - timeStart;
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String get(String url, int connectTimeout, int readTimeout) throws Exception {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "token");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            in.close();
        } catch (Exception e) {
            result = e.getMessage();
            if (in != null) {
                in.close();
            }
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                result = e.getMessage();
                throw e;
            }
        }
        return result;
    }
    /**
     *
     * @param amp
     *  待写入文件的map
     * @param separators
     *  map写入文件后key和value的分隔符
     * @param filePath
     *  文件路径
     */
    public static void writeMapToFile(Map<Object, Object> amp, String filePath, String separators){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 对字符串进行无效信息清洗
     *
     * @param str
     * @return
     */
    public static String clenaString(String str) {
        str = str.replace(" ", "").replace("\t", "").replace("\r", "").replace("\n", "").replace("　", "").trim();
        return str;
    }

    /**
     * 写文件，指定文件路径
     * 以字符流的方式
     * @param path
     * @param content
     */
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

    /**
     * 读文件(按行读取，保存到list中，读取文件全部内容)
     *
     * @param filePath 文件路径
     */
    public static List<String> readFileToList(String filePath) {
        List<String> list = new ArrayList<String>();
        File file = new File(filePath);
        BufferedReader reader = null;
        if (file.exists()) {
            try {
                reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), "UTF-8"));
                String temp = null;
                while (null != (temp = reader.readLine())) {
                    list.add(temp.trim());
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
        return list;
    }

    public static boolean isContains(String value, String[] keys) {
        for (String key : keys) {
            if (value.contains(key)) {
                return true;
            }
        }
        return false;
    }
}
