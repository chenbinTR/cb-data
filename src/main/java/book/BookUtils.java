package book;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.ExcelEntity;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wangsx
 * @Title: book.BookUtils
 * @Description: TODO
 * @date 2019/12/20
 */
public class BookUtils {
    /**
     * @param fileName
     * @param sheetNum
     * @return
     */
    public static List<ExcelEntity> readXml(String fileName, int sheetNum) {
        List<ExcelEntity> list = new ArrayList<ExcelEntity>();
        try {
            InputStream input = new FileInputStream(fileName);
            Workbook wb;
            if (fileName.toLowerCase().endsWith("xlsx")) {
                wb = new XSSFWorkbook(input);
            } else {
                wb = new HSSFWorkbook(input);
            }
            // 获得表单
            Sheet sheet = wb.getSheetAt(sheetNum);
            // 获得表单的迭代器
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
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
                if (StringUtils.isNotBlank(row0) && row0.toUpperCase().equals("END")) {
                    continue;
                }
                if (!te.isBlank()) {
                    list.add(te);
                }
            }
            FileOutputStream os = new FileOutputStream(fileName);
            wb.write(os);
            os.close();
        } catch (Exception ex) {
            System.err.println(fileName);
//            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return list;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringCellValue(Cell cell) {
        if (null == cell) {
            return "";
        }
        String strCell;
        switch (cell.getCellType()) {
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            case NUMERIC:
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
            strCell = "";
        }
        return strCell;
    }

    /**
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
}
