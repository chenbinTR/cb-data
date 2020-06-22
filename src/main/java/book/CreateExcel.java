package book;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import utils.ExcelEntity;
import utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-09-12
 * @see
 * @since
 */
public class CreateExcel {
    private static String path = "Q:\\book3\\";
    public static void main(String[] args) {
        File file = new File(path);
        String[] files = file.list();
        for (String fileName : files) {
            System.out.println(fileName);
            // 单个文件处理
            List<String> fileContents = Utils.readFileToList(path + fileName);
            List<ExcelEntity> excelEntityList = new ArrayList<>();
            for (String line : fileContents) {
                try {
                    String[] rows = line.split("\t");
                    String id = rows[0];
                    String text = rows[1];
                    String question = rows[2];
                    String answer;
                    if (rows.length == 3) {
                        answer = "/";
                    } else {
                        answer = rows[3];
                    }

                    ExcelEntity excelEntity = new ExcelEntity();
                    excelEntity.setValue0(id);
                    excelEntity.setValue1(text);
                    excelEntity.setValue2(question);
                    excelEntity.setValue3(answer);
                    excelEntityList.add(excelEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(line);
                }
            }
            Utils.writeExcel(path + fileName.replace(".txt", "") + ".xls", excelEntityList);
        }
    }
}
