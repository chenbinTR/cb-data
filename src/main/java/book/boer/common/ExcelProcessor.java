package book.boer.common;

import book.BookUtils;
import book.boer.common.BookAreaNormalizeProcessor;
import book.entity.BookAreaEntity;
import book.entity.BookParams;
import cn.hutool.core.convert.Convert;
import org.apache.commons.lang3.StringUtils;
import utils.ExcelEntity;

import java.util.*;

/**
 * @author ChenOT
 * @date 2019-12-16
 * @see
 * @since
 */
public class ExcelProcessor {
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
    public static Integer getPageNum(String page) {
        if (PAGE_NUM_MAP.get(page.toUpperCase()) == null) {
            System.err.println("不存在页码对应关系" + page);
        }
        return PAGE_NUM_MAP.get(page);
    }

    /**
     * 读取excel内容
     *
     * @param path
     * @return key：页码
     * value：页码对应的所有内容（音频文件）
     */
    public synchronized static Map<String, List<BookAreaEntity>> readBookAreaEntity(String path, BookParams bookParams) {
        PAGE_NUM_MAP.clear();
        // 读取excel文件
        List<ExcelEntity> excelEntityList = BookUtils.readXml(path, 0);

        Map<String, List<BookAreaEntity>> bookMap = new LinkedHashMap<>();
        String page = "";
        int pageNum = 0;
        List<BookAreaEntity> bookEntityList = new ArrayList<>();
        for (int i = 0; i < excelEntityList.size(); i++) {
            ExcelEntity excelEntity = excelEntityList.get(i);
            String areaId = Convert.toDBC(excelEntity.getValue1());
            String enId = excelEntity.getValue3();
            String enContent = excelEntity.getValue4();
            String chId = excelEntity.getValue5();
            String chContent = excelEntity.getValue6();
            // 如果有中文内容，没有英文内容，则英文内容与中文内容相同
            if (StringUtils.isBlank(enId) && StringUtils.isBlank(enContent) && StringUtils.isNotBlank(chId) && StringUtils.isNotBlank(chContent)) {
                enId = chId;
                enContent = chContent;
            }
            // 跳过表头
            if (areaId.equals("框号")) {
                continue;
            }
            // 数据异常判断
            if (StringUtils.isBlank(areaId) && StringUtils.isNotBlank(enContent + enId + chId + chContent)) {
                System.err.println(String.format("[Excel数据异常][%s][%s]", path, excelEntity));
            }
            if (StringUtils.isBlank(page)) {
                page = excelEntity.getValue7();
                PAGE_NUM_MAP.put(page.toUpperCase(), ++pageNum);
            }
            BookAreaEntity bookEntity = new BookAreaEntity();
            bookEntity.setAreaId(areaId);
            bookEntity.setEnId(enId);
            bookEntity.setChContent(chContent);
            bookEntity.setChId(chId);
            bookEntity.setPage(page);
            bookEntity.setEnContent(enContent);

            // 判断一页结束
            if (StringUtils.isBlank(areaId + enId + enId + chContent + chId + chContent)
                    || i == excelEntityList.size() - 1) {
                if (i == excelEntityList.size() - 1) {
                    bookEntityList.add(bookEntity);
                }
                if (bookEntityList.size() > 0) {
                    List<BookAreaEntity> listTemp = new ArrayList<>();
                    listTemp.addAll(bookEntityList);
                    bookMap.put(page, normalizePageContent(listTemp, bookParams));
                }
                bookEntityList.clear();
                page = "";
                continue;
            }
            bookEntityList.add(bookEntity);
        }

        bookMap.forEach((k, v) -> {
            v.forEach(entity -> {
                if (StringUtils.isBlank(entity.getEnId()) || StringUtils.isBlank(entity.getEnContent())) {
                    System.err.println(path+"   英文内容不完整：" + entity.toString());
                }
            });
        });

        return bookMap;
    }

    /**
     * 对每页的内容进行归一化
     * 3中特殊情况
     *
     * @return
     */
    private static List<BookAreaEntity> normalizePageContent(final List<BookAreaEntity> sourceBookEntityList, BookParams bookParams) {
        // 首先处理 "1,2",对应共同的显示内容
        List<BookAreaEntity> bookEntities2 = BookAreaNormalizeProcessor.specialOne(sourceBookEntityList, bookParams);
        List<BookAreaEntity> bookEntities = BookAreaNormalizeProcessor.specialTwo(bookEntities2, bookParams);
        List<BookAreaEntity> bookEntities3 = BookAreaNormalizeProcessor.specailThree(bookEntities, bookParams);
        return bookEntities3;
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
        String page = "";
        int pageNum = 0;
        List<BookAreaEntity> bookEntityList = new ArrayList<>();
        for (int i = 0; i < excelEntityList.size(); i++) {
            ExcelEntity excelEntity = excelEntityList.get(i);
            String areaId = Convert.toDBC(excelEntity.getValue1());
            String enId = excelEntity.getValue3();
            String enContent = excelEntity.getValue4();
            String chId = excelEntity.getValue5();
            String chContent = excelEntity.getValue6();

            // 如果有中文内容，没有英文内容，则英文内容与中文内容相同
            if (StringUtils.isAllBlank(chId, chContent)) {
                chId = enId;
                chContent = enContent;
            } else {
                enId = chId;
                enContent = chContent;
            }

            // 跳过表头
            if (areaId.equals("框号")) {
                continue;
            }
            // 数据异常判断
            if (StringUtils.isBlank(areaId) && StringUtils.isNotBlank(enContent + enId + chId + chContent)) {
                System.err.println(String.format("[Excel数据异常][%s][%s]", path, excelEntity));
            }
            if (StringUtils.isBlank(page)) {
                page = excelEntity.getValue7();
                PAGE_NUM_MAP.put(page.toUpperCase(), ++pageNum);
            }
            BookAreaEntity bookEntity = new BookAreaEntity();
            bookEntity.setAreaId(areaId);
            bookEntity.setEnId(enId);
            bookEntity.setChContent(chContent);
            bookEntity.setChId(chId);
            bookEntity.setPage(page);
            bookEntity.setEnContent(enContent);

            // 判断一页结束
            if (StringUtils.isBlank(areaId + enId + enId + chContent + chId + chContent)
                    || i == excelEntityList.size() - 1) {
                if (i == excelEntityList.size() - 1) {
                    bookEntityList.add(bookEntity);
                }
                if (bookEntityList.size() > 0) {
                    List<BookAreaEntity> listTemp = new ArrayList<>();
                    listTemp.addAll(bookEntityList);
                    bookMap.put(page, normalizePageContent(listTemp, bookParams));
                }
                bookEntityList.clear();
                page = "";
                continue;
            }
            if (StringUtils.isAllBlank(chId, chContent)) {
                System.err.println("中文内容列空");
            }
            bookEntityList.add(bookEntity);
        }
        return bookMap;
    }
}
