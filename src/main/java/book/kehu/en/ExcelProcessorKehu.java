package book.kehu.en;

import book.BookUtils;
import book.entity.BookAreaEntity;
import book.entity.BookParams;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import utils.ExcelEntity;

import java.util.*;

/**
 * @author ChenOT
 * @date 2019-12-16
 * @see
 * @since
 */
public class ExcelProcessorKehu {
    /**
     * 保存excel中的页码编号，和数字的对应关系
     */
    private static final Map<String, Integer> PAGE_NUM_MAP = new HashMap<>();

    /**
     * 获取页码和数字的对应关系
     *
     * @param page
     * @return
     */
    static Integer getPageNum(String page) {
        if (PAGE_NUM_MAP.get(page.toUpperCase()) == null) {
            System.err.println("不存在页码对应关系" + page);
        }
        return PAGE_NUM_MAP.get(page);
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(readBookAreaEntityCh("E:\\logs\\人教版小学数学三年级上册_吴刘桃.xlsx", null)));
    }

    /**
     * 新客户英语读取excel内容
     *
     * @param path
     * @return key：页码
     * value：页码对应的所有内容（音频文件）
     */
    synchronized static Map<String, List<BookAreaEntity>> readBookAreaEntity(String path, BookParams bookParams) {
        PAGE_NUM_MAP.clear();
        // 读取excel文件
        List<ExcelEntity> excelEntityList = BookUtils.readXml(path, 0);
        Map<String, List<BookAreaEntity>> bookMap = new LinkedHashMap<>();
        for (int i = 0; i < excelEntityList.size(); i++) {
            ExcelEntity excelEntity = excelEntityList.get(i);
            String areaId = Convert.toDBC(excelEntity.getValue2());
            if (StringUtils.isBlank(areaId) || areaId.trim().equals("框号")) {
                continue;
            }
            String enId = excelEntity.getValue4();
            String enContent = excelEntity.getValue5();
            String chId = excelEntity.getValue6();
            String chContent = excelEntity.getValue7();
            String page = excelEntity.getValue3();
            // 如果有中文内容，没有英文内容，则英文内容与中文内容相同
//            if(StringUtils.isBlank(enId)&&StringUtils.isBlank(enContent)&&StringUtils.isNotBlank(chId)&&StringUtils.isNotBlank(chContent)){
//                enId = chId;
//                enContent = chContent;
//            }
            BookAreaEntity bookEntity = new BookAreaEntity();
            bookEntity.setAreaId(areaId);
            bookEntity.setEnId(enId);
            bookEntity.setChContent(chContent);
            bookEntity.setChId(chId);
            bookEntity.setPage(page);
            bookEntity.setEnContent(enContent);
            List<BookAreaEntity> bookAreaEntityList = (List<BookAreaEntity>) MapUtils.getObject(bookMap, page, new ArrayList<>());
            bookAreaEntityList.add(bookEntity);
            bookMap.put(page, bookAreaEntityList);
            if (StringUtils.isAllBlank(enId, enContent, chId, chContent)) {
                System.err.println("中英文内容均为空");
            }
        }
        int pageNum = 0;
        for (String key : bookMap.keySet()) {
            PAGE_NUM_MAP.put(key, ++pageNum);
        }

        return bookMap;
    }

    /**
     * 读取excel内容
     *
     * @param path
     * @return key：页码
     * value：页码对应的所有内容（音频文件）
     */
    public synchronized static Map<String, List<BookAreaEntity>> readBookAreaEntityCh(String path, BookParams bookParams) {
        PAGE_NUM_MAP.clear();

        // 读取excel文件
        List<ExcelEntity> excelEntityList = BookUtils.readXml(path, 0);
        Map<String, List<BookAreaEntity>> bookMap = new LinkedHashMap<>();
        for (int i = 0; i < excelEntityList.size(); i++) {
            ExcelEntity excelEntity = excelEntityList.get(i);
            String areaId = Convert.toDBC(excelEntity.getValue2());
            if (StringUtils.isBlank(areaId) || areaId.trim().equals("框号")) {
                continue;
            }
            String enId = "";
            String enContent = "";
            String chId = excelEntity.getValue4();
            String chContent = excelEntity.getValue5();
            String page = excelEntity.getValue3();
            // 如果有中文内容，没有英文内容，则英文内容与中文内容相同
            if (StringUtils.isNotBlank(chId) && StringUtils.isNotBlank(chContent)) {
                enId = chId;
                enContent = chContent;
            } else {
                System.err.println("缺少语文音频或内容-行序号：" + excelEntity.getValue0());
            }
            BookAreaEntity bookEntity = new BookAreaEntity();
            bookEntity.setAreaId(areaId);
            bookEntity.setEnId(enId);
            bookEntity.setChContent(chContent);
            bookEntity.setChId(chId);
            bookEntity.setPage(page);
            bookEntity.setEnContent(enContent);
            List<BookAreaEntity> bookAreaEntityList = (List<BookAreaEntity>) MapUtils.getObject(bookMap, page, new ArrayList<>());
            bookAreaEntityList.add(bookEntity);
            bookMap.put(page, bookAreaEntityList);
            if (StringUtils.isAllBlank(enId, enContent, chId, chContent)) {
                System.err.println("中英文内容均为空");
            }
        }
        int pageNum = 0;
        for (String key : bookMap.keySet()) {
            PAGE_NUM_MAP.put(key, ++pageNum);
        }
        return bookMap;
    }
}
