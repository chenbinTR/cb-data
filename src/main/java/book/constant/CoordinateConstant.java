package book.constant;

import cn.hutool.core.util.NumberUtil;
import utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChenOT
 * @date 2019-12-17
 * @see
 * @since
 */
public class CoordinateConstant {
    private static final Map<String, Integer> coordinateMap = new HashMap<>();
    private static final Map<String, Integer> coordinateMapNew = new HashMap<>();
    public static final String SOURCE_COORDIATE_FILE = "E:\\BOOK_DATA\\已处理\\DEFINE-32.H";
    public static final String SOURCE_COORDIATE_NEW_FILE = "E:\\BOOK_DATA\\已处理\\标准框2-DEFINE-00.H";
    /**
     * 初始化
     */
    static{
        List<String> lines = Utils.readFileToList(SOURCE_COORDIATE_FILE);
        List<String> linesNew = Utils.readFileToList(SOURCE_COORDIATE_NEW_FILE);
        for (String line : lines) {
            line = line.toUpperCase();
            if (!line.startsWith("X") && !line.startsWith("Y")) {
                continue;
            }
            String[] items = line.split("\t");
            coordinateMap.put(items[0].trim(), Integer.valueOf(items[2].substring(0, 4)));
        }
        for (String line : linesNew) {
            line = line.toUpperCase();
            if (!line.startsWith("X") && !line.startsWith("Y")) {
                continue;
            }
            String[] items = line.split("\t");
            coordinateMapNew.put(items[0].trim(), Integer.valueOf(items[2].substring(0, 4)));
        }
    }

    public static void main(String[] args) {
        System.out.println(coordinateMap.toString());
        System.out.println(NumberUtil.roundStr((double) CoordinateConstant.getCoordinateValue("X1471", 1) / (double) 1, 6));
    }
    /**
     * 获取坐标的映射关系
     * @param key
     * @return
     */
    public static Integer getCoordinateValue(String key, int type){
        Integer value;
        if(type == 0){
            value = coordinateMap.get(key);
        }else{
            value = coordinateMapNew.get(key);
        }
        if(null == value){
            System.err.println(String.format("[.h文件读取像素坐标映射关系错误][%s]",key));
        }
        return value;
    }

}
