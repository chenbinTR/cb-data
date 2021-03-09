package utils;

import com.alibaba.fastjson.JSONObject;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by cb on 2017-03-09.
 */
public class Utils {
    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    //将文件转化为单声道
    public static File executeSingleChannel(File source, String desFileName) throws EncoderException {
        File target = new File(desFileName);
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setChannels(new Integer(1));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);
        return target;
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
     * 创建excel
     *
     * @param fileName
     */
    public static void createExcel(String fileName) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.createSheet("sheet1");
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 写excel文件
     *
     * @param fileName 文件路径+文件名
     * @param list     待写内容
     */
    public static void writeExcel(String fileName, List<ExcelEntity> list) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                HSSFWorkbook workbook = new HSSFWorkbook();
                FileOutputStream fileOut = new FileOutputStream(fileName);
                workbook.write(fileOut);
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream input = new FileInputStream(fileName);
        Workbook wb = new HSSFWorkbook(input);
        Sheet sheet1 = wb.getSheet("sheet1");
        if (null == sheet1) {
            sheet1 = wb.createSheet();
        }
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet1.createRow(i);
            row.createCell(0).setCellValue(list.get(i).getValue0());
            row.createCell(1).setCellValue(list.get(i).getValue1());
            row.createCell(2).setCellValue(list.get(i).getValue2());
            row.createCell(3).setCellValue(list.get(i).getValue3());
//                row.createCell(4).setCellValue(list.get(i).getValue4());
//                row.createCell(5).setCellValue(list.get(i).getValue5());
//                row.createCell(6).setCellValue(list.get(i).getValue6());
        }
        OutputStream stream = new FileOutputStream(fileName);
        wb.write(stream);
        stream.close();
    }

    /**
     * 文件合并
     *
     * @param sourceFileNames
     * @param destFileName
     * @throws Exception
     */
    public static void combinMp3File(List<String> sourceFileNames, String destFileName) {
        // 判断源文件是否有效
        boolean isExistSource = false;
        for (String sourceFileName : sourceFileNames) {
            if (StringUtils.isBlank(sourceFileName)) {
                continue;
            }
            isExistSource = true;
        }
        if (!isExistSource) {
            return;
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(destFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String sourceFileName : sourceFileNames) {
            if (StringUtils.isBlank(sourceFileName)) {
                continue;
            }
            File f = new File(sourceFileName);
            if (!f.exists()) {
                System.err.println("文件不存在：" + sourceFileName + destFileName);
                continue;
            }
            FileInputStream sourceFileInputStream = null;
            try {
                sourceFileInputStream = new FileInputStream(new File(sourceFileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] b = new byte[1024];
            int len;
            try {
                while ((len = sourceFileInputStream.read(b)) != -1) {
                    for (int i = 0; i < len; i++) {
                        out.write(b[i]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sourceFileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String destFolderNew = destFileName.substring(0, destFileName.lastIndexOf(File.separator) + 1).replace("Audio_1", "Audio");
            String fileName = destFileName.substring(destFileName.lastIndexOf(File.separator) + 1);
            File file = new File(destFolderNew);
            if (file.exists()) {
                file.mkdirs();
            }
            executeSingleChannel(new File(destFileName), destFolderNew + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(JSONObject.toJSONString(sourceFileNames));
            System.err.println(destFileName);
            throw new RuntimeException("executeSingleChannel error");
        }
    }

    /**
     * 文件合并
     *
     * @param sourceFileNames
     * @param destFileName
     * @throws Exception
     */
    public static void combinFile(List<String> sourceFileNames, String destFileName) {
        // 判断源文件是否有效
        boolean isExistSource = false;
        for (String sourceFileName : sourceFileNames) {
            if (StringUtils.isBlank(sourceFileName)) {
                continue;
            }
            isExistSource = true;
        }
        if (!isExistSource) {
            return;
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(destFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String sourceFileName : sourceFileNames) {
            if (StringUtils.isBlank(sourceFileName)) {
                continue;
            }
            File f = new File(sourceFileName);
            if (!f.exists()) {
                System.err.println("文件不存在：" + sourceFileName + destFileName);
                continue;
            }
            FileInputStream sourceFileInputStream = null;
            try {
                sourceFileInputStream = new FileInputStream(new File(sourceFileName));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] b = new byte[1024];
            int len;
            try {
                while ((len = sourceFileInputStream.read(b)) != -1) {
                    for (int i = 0; i < len; i++) {
                        out.write(b[i]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sourceFileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param fileName
     * @param sheetNum
     * @return
     */
    public static List<ExcelEntity> readXml(String fileName, int sheetNum) {
        List<ExcelEntity> list = new ArrayList<ExcelEntity>();
        boolean isE2007 = false;
        if (fileName.endsWith("xlsx")) {
            isE2007 = true;
        }
        try {
            InputStream input = new FileInputStream(fileName);
            Workbook wb = null;
            // 根据文件格式(2003或者2007)来初始化
            if (isE2007) {
                wb = new XSSFWorkbook(input);
            } else {
                wb = new HSSFWorkbook(input);
            }
            // 获得表单
            Sheet sheet = wb.getSheetAt(sheetNum);
            // 获得表单的迭代器
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next(); // 获得当前行
                //读取第三列，转成map，并排序
                String row0 = getStringCellValue(row.getCell(0)).trim();
                String row1 = getStringCellValue(row.getCell(1)).trim();
                String row2 = getStringCellValue(row.getCell(2)).trim();
                String row3 = getStringCellValue(row.getCell(3)).trim();
                String row4 = getStringCellValue(row.getCell(4)).trim();
                String row5 = getStringCellValue(row.getCell(5)).trim();
                String row6 = getStringCellValue(row.getCell(6)).trim();
                String row7 = getStringCellValue(row.getCell(7)).trim();
                ExcelEntity te = new ExcelEntity();
                te.setValue0(row0);
                te.setValue1(row1);
                te.setValue2(row2);
                te.setValue3(row3);
                te.setValue4(row4);
                te.setValue5(row5);
                te.setValue6(row6);
                te.setValue7(row7);
                list.add(te);
            }
            FileOutputStream os = new FileOutputStream(fileName);
            wb.write(os);
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    public static String getStringCellValue(Cell cell) {
        if (null == cell) {
            return "";
        }
        String strCell = "";
        switch (cell.getCellType()) {
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            case NUMERIC:
//                strCell = String.valueOf(cell.getNumericCellValue());
                DecimalFormat df = new DecimalFormat("0");
                strCell = df.format(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (StringUtils.isBlank(strCell)) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取指定路径下的所有文件/文件夹名字
     *
     * @param path
     * @return
     */
    public static List<String> getPathFileName(String path) {
        File file = new File(path);
        List<String> list = new ArrayList<String>();
        if (!file.exists()) {
            return list;
        }

        File f[] = file.listFiles();
        for (int i = 0; i < f.length; i++) {
            File fs = f[i];
            if (fs.isDirectory()) {
            } else {
                list.add(fs.getAbsolutePath());
            }
        }
        return list;
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
     * httpget
     *
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl
                    .openConnection();

            connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(3000);
            connection.connect();

            // 取得输入流，并使用Reader读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
            return sb.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * 写文件
     *
     * @param path
     * @param contents 多个字段， 以 \t 隔开
     */
    public static void writeToTxt(String path, String... contents) {
        int length = contents.length;
        String content = "";
        int index = 0;
        for (String item : contents) {
            index++;
            if (index < length) {
                content = content + item + "\t";
            } else {
                content = content + item;
            }
        }

        File file = new File(path);
        try (
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter writer = new BufferedWriter(fw)
        ) {
            writer.write(content);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文件(按行读取，保存到list中，读取文件全部内容)
     *
     * @param filePath 文件路径
     */
    public static Set<String> readFileToSet(String filePath) {
        Set<String> set = new HashSet<String>();
        File file = new File(filePath);
        readFile(file, set, "UTF-8");
        return set;
    }

    private static void readFile(File file, Collection collection, String encoding) {
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), encoding))) {
                String temp;
                while (null != (temp = reader.readLine())) {
                    if (StringUtils.isNotBlank(temp.trim())) {
                        collection.add(temp.trim());
                    }
                }
            } catch (IOException e) {
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
        readFile(file, list, "UTF-8");
        return list;
    }

    /**
     * 读文件(按行读取，保存到list中，读取文件全部内容)
     *
     * @param filePath 文件路径
     */
    public static List<String> readFileToList(String filePath, String encoding) {
        List<String> list = new ArrayList<String>();
        File file = new File(filePath);
        readFile(file, list, encoding);
        return list;
    }

    private static List<String> listFile(List<String> lstFileNames, File f, String suffix, boolean isdepth) {
        // 若是目录, 采用递归的方法遍历子目录
        if (f.isDirectory()) {
            File[] t = f.listFiles();

            for (int i = 0; i < t.length; i++) {
                if (isdepth || t[i].isFile()) {
                    listFile(lstFileNames, t[i], suffix, isdepth);
                }
            }
        } else {
            String filePath = f.getAbsolutePath();
            if (!suffix.equals("")) {
                int begIndex = filePath.lastIndexOf("."); // 最后一个.(即后缀名前面的.)的索引
                String tempsuffix = "";

                if (begIndex != -1) {
                    tempsuffix = filePath.substring(begIndex + 1, filePath.length());
                    if (tempsuffix.equals(suffix)) {
                        lstFileNames.add(filePath);
                    }
                }
            } else {
                lstFileNames.add(filePath);
            }
        }
        return lstFileNames;
    }

    /**
     * NIO way
     * 读取文件
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray2(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
