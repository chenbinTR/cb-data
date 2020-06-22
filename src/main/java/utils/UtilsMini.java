package utils;

import java.io.*;
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

//    /**
//     * 判断是否由单个字的叠词组成
//     *
//     * @param que 10 由简单的叠词组成
//     */
//    public static int checkRepeatModalWord(String que) {
//        //去掉所有的标点符号
//        String queTemp = que.replaceAll("[\\pP\\p{Punct}]", "");
//
//        //纯语气词判断
//        String keys = "哎|哟|喂|哈|呵|啊|吧|罢|呗|啵|的|啦|了|嘞|哩|咧|咯|啰|喽|吗|嘛|嚜|么|麽|哪|呢|呐|呵|哈|兮|噻|哉|嘻|嘿|哟|吼|恩|嗯|额|呀|噢|哇|哼";
//        if (StringUtils.isBlank(TuringStringUtils.replaceKeyWordAll(que, keys, ""))) {
//            return 10;
//        }
//
//        //如果去掉标点符号，长度为1
//        if (queTemp.length() == 1) {
//            return 0;
//        }
//
//
//        //单个语气词重复类型判断
//        char[] ques = queTemp.toCharArray();
//        Set set = new HashSet();
//        for (int i = 0; i < ques.length; i++) {
//            set.add(ques[i]);
//        }
//
//        if (set.size() == 1) {
//            return 10;
//        }
//
//        //如果去重长度变得很小
//        if ((set.size() <= que.length() / 2) && que.length() > 4) {
//            return 10;
//        }
//        return 0;
//    }
//
//    /**
//     * 判断是否包含[]【】
//     *
//     * @param str
//     * @return
//     */
//    public static boolean isContainsFace(String str) {
//        Pattern p = Pattern.compile("\\[.*\\]");
//        Matcher m = p.matcher(str);
//        if (m.find()) {
//            return true;
//        }
//
//        p = Pattern.compile("【.*】");
//        m = p.matcher(str);
//        if (m.find()) {
//            return true;
//        }
//
//
//        return false;
//    }
//
//    /**
//     * 色黄暴敏感判断
//     * 11
//     */
//    public static int checkSex(String que) {
//        String url = "";
//        try {
//            que = URLEncoder.encode(que, "UTF-8");
//            url = "http://192.168.10.30:8080/nlpfilter/sex-classifier?question=" + que;
//            String result = TuringHttpUtils.get(url, 2000, 2000);
//            if (result.endsWith("negative")) {
//                return 11;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println(url);
//            return 0;
//        }
//        return 0;
//    }
//
//    /**
//     * 字符串是否为纯汉字（不包括标点符号等中文字符，纯汉字）
//     * @param str
//     * @return
//     */
//    public static boolean isChinese(String str) {
//
//        Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
//        Matcher m = p.matcher(str);
//        if (m.matches()) {
//            return true;
//        }
//        return false;
//    }
//    /**
//     * 判断字符串是否包含汉字
//     *
//     * @param str
//     * @return
//     */
//    public static boolean isContainChinese(String str) {
//
//        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
//        Matcher m = p.matcher(str);
//        if (m.find()) {
//            return true;
//        }
//        return false;
//    }

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
