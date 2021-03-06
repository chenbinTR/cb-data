package book.kehu.en;

import book.entity.AsmEntity;
import book.entity.AsmInfoEntity;
import utils.Utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ChenOT
 * @date 2019-12-16
 * @see
 * @since
 */
public class AsmProcessorKehu {
    public static void main(String[] args) {
        readAsmFile("E:\\BOOK_DATA\\已处理\\已修正\\人教-test\\三年级\\coordsrc\\P001V.asm");
    }

    /**
     * 读取单个asm文件
     *
     * @param asmFilePath asm文件全路径
     * @return key：域号
     * value：矩形集合
     */
    static AsmInfoEntity readAsmFile(String asmFilePath) {
        List<String> lines = Utils.readFileToList(asmFilePath);
        Map<Integer, List<AsmEntity>> asmMap = new LinkedHashMap<>();
        List<AsmEntity> asmEntityList = new ArrayList<>();
        boolean isInDomain = false;
        int kuangNum = 0;
        for (String line : lines) {
            // 判断域开始
            if (line.endsWith("_BEGIN:")) {
                isInDomain = true;
                // 提取框号
                String kuangNumStr = line.replaceFirst("_", "");
                kuangNumStr = kuangNumStr.substring(0, kuangNumStr.indexOf("_"));
                kuangNum = Integer.valueOf(kuangNumStr);
            }
            if (!isInDomain) {
                continue;
            }
            if (line.toUpperCase().indexOf("X") > -1 && line.toUpperCase().indexOf("Y") > -1) {
                line = line.substring(line.indexOf("X"));
                String content = line
                        .replace("DW", "")
                        .replace(" ", "")
                        .replace("\t", "")
                        .replace("+$8000", "")
                        .trim();
                String[] xy = content.split(",");

                List<String> xyNew = new ArrayList<>();
                for (String s : xy) {
                    String tmp = s;
                    if(s.indexOf("+")>-1 || s.indexOf("-")>-1){
                        String re = s.replace("X","").replace("Y","");
                        ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
                        try {
                            tmp = se.eval(re).toString();
                        } catch (ScriptException e) {
                            e.printStackTrace();
                        }
                    }
                    xyNew.add(tmp);
                }

                
                AsmEntity asmEntity = new AsmEntity();
                asmEntity.setTopLeftX(xyNew.get(0).toLowerCase().replace("x", "").replace("y", ""));
                asmEntity.setTopLeftY(xyNew.get(1).toLowerCase().replace("x", "").replace("y", ""));
                asmEntity.setLowerRightX(xyNew.get(2).toLowerCase().replace("x", "").replace("y", ""));
                asmEntity.setLowerRightY(xyNew.get(3).toLowerCase().replace("x", "").replace("y", ""));

                asmEntityList.add(asmEntity);
            }
            // 判断域结束
            if (line.endsWith("_END") || line.endsWith("_END:")) {
                isInDomain = false;
                List<AsmEntity> asmEntities = new ArrayList<>(asmEntityList.size());
                asmEntities.addAll(asmEntityList);
                asmEntityList.clear();
                asmMap.put(kuangNum, asmEntities);
            }
        }
        // 判断对应坐标转换规则，使用的文件
        int type = 0;
        int index = 0;
        if (asmMap.size() > 1) {
            int firstAreaId = 0;
            for (Integer key : asmMap.keySet()) {
                if (index == 0) {
                    firstAreaId = key;
                }
                // 判断第二个key，是否大约第一个
                if (index == 1) {
                    if (key > firstAreaId) {
                        type = 1;
                    }
                    break;
                }
                index++;
            }
        }
        return new AsmInfoEntity(asmMap, type);
    }
}
