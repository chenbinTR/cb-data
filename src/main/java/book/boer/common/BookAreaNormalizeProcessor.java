package book.boer.common;

import book.entity.BookAreaEntity;
import book.entity.BookParams;
import cn.hutool.core.convert.Convert;
import org.apache.commons.lang3.StringUtils;
import utils.CopyUtils;

import java.util.*;

/**
 * 每行数据进行归一化
 *
 * @author ChenOT
 * @date 2019-12-15
 * @see
 * @since
 */
public class BookAreaNormalizeProcessor {
    /**
     * 归一化，特殊一，多个framdId公用相同的内容，且内容是显式的
     */
    public static List<BookAreaEntity> specialOne(final List<BookAreaEntity> sourceBookEntityList, BookParams bookParams) {
        List<BookAreaEntity> bookAreaEntities = new ArrayList<>();
        Map<String, BookAreaEntity> frameIdMap = new LinkedHashMap<>();
        for (BookAreaEntity bookEntity : sourceBookEntityList) {
            frameIdMap.put(bookEntity.getAreaId(), bookEntity);
        }
        for (BookAreaEntity bookEntity : sourceBookEntityList) {
            String areaId = bookEntity.getAreaId();
            String enId = bookEntity.getEnId();
            if (areaId.contains(",") && StringUtils.isNotBlank(enId)) {
                String[] ss = areaId.trim().split(",");
                for (String s : ss) {
                    BookAreaEntity be = CopyUtils.copy(bookEntity, BookAreaEntity.class);
                    be.setAreaId(s);
                    bookAreaEntities.add(be);
                }
            } else {
                bookAreaEntities.add(bookEntity);
            }
        }
        return bookAreaEntities;
    }

    /**
     * 归一化，特殊二，frameId正常，enContent对应的多个其他frameId
     */
    public static List<BookAreaEntity> specialTwo(final List<BookAreaEntity> sourceBookAreaEntityList, BookParams bookParams) {
        List<BookAreaEntity> bookEntities2 = new ArrayList<>();
        Map<String, BookAreaEntity> frameIdMap = new LinkedHashMap<>();
        for (BookAreaEntity bookEntity : sourceBookAreaEntityList) {
            frameIdMap.put(bookEntity.getAreaId(), bookEntity);
        }
        for (BookAreaEntity bookEntity : sourceBookAreaEntityList) {
            String areaId = bookEntity.getAreaId();
            String enId = bookEntity.getEnId();
            String enContent = bookEntity.getEnContent();
            // 判断areaId = 5, enContent=3,4 的情况
            if (!areaId.contains(",") && StringUtils.isBlank(enId) && StringUtils.isNotBlank(enContent)) {
                String[] fs = Convert.toDBC(enContent).split(",");
                List<String> enIds = new ArrayList<>();
                List<String> chIds = new ArrayList<>();
                StringBuilder sbEN = new StringBuilder();
                StringBuilder sbCH = new StringBuilder();
                for (String fId : fs) {
                    BookAreaEntity be = frameIdMap.get(fId);
                    if (Arrays.asList(fs).contains(areaId) || be == null || StringUtils.isBlank(be.getEnId())) {
                        System.err.println(String.format("[%s][特殊二错误][%s]", bookParams.getBookName(), bookEntity));
                    }
                    enIds.add(be.getEnId());
                    chIds.add(be.getChId());
                    if (StringUtils.isNotBlank(be.getEnContent())) {
                        sbEN.append(be.getEnContent() + "\r\n");
                    }
                    if (StringUtils.isNotBlank(be.getChContent())) {
                        sbCH.append(be.getChContent() + "\r\n");
                    }
                }
                // 将对应多个域的音频，放到enIds
                bookEntity.setEnIDs(enIds);
                bookEntity.setChIDs(chIds);
                // 将丢应多个域的英文文字拼接到一起
                bookEntity.setEnContent(sbEN.toString());
                bookEntity.setChContent(sbCH.toString());
                bookEntities2.add(bookEntity);
            } else {
                bookEntities2.add(bookEntity);
            }
        }
        return bookEntities2;
    }

    /**
     * 归一化，特殊三，同时存在特殊一和特殊二
     */
    public static List<BookAreaEntity> specailThree(final List<BookAreaEntity> sourceBookEntityList, BookParams bookParams) {
        List<BookAreaEntity> bookEntities3 = new ArrayList<>();
        // 将bookEntityes以frameId为key，转为map
        Map<String, BookAreaEntity> frameIdMap = new LinkedHashMap<>();
        for (BookAreaEntity bookEntity : sourceBookEntityList) {
            frameIdMap.put(bookEntity.getAreaId(), bookEntity);
        }
        for (BookAreaEntity bookEntity : sourceBookEntityList) {
            String areaId = bookEntity.getAreaId();
            String enId = bookEntity.getEnId();
            String enContent = bookEntity.getEnContent();
            if (areaId.contains(",") && StringUtils.isBlank(enId) && StringUtils.isNotBlank(enContent)) {
                //
                String[] ss = areaId.split(",");
                for (String s : ss) {
                    // 将frameId分别单置
                    BookAreaEntity be = CopyUtils.copy(bookEntity, BookAreaEntity.class);
                    be.setAreaId(s);
                    // 重复特殊二 的逻辑
                    String[] fs = enContent.split(",");
                    List<String> enIds = new ArrayList<>();
                    List<String> chIds = new ArrayList<>();
                    StringBuilder sbEN = new StringBuilder();
                    StringBuilder sbCH = new StringBuilder();
                    for (String fId : fs) {
                        BookAreaEntity child = frameIdMap.get(fId);
                        if (child == null || StringUtils.isBlank(child.getEnId())) {
                            System.err.println(String.format("[%s][特殊三错误][%s]", bookParams.getBookName(), bookEntity));
                        }
                        enIds.add(child.getEnId());
                        chIds.add(child.getChId());
                        if (StringUtils.isNotBlank(child.getEnContent())) {
                            sbEN.append(child.getEnContent() + "\r\n");
                        }
                        if (StringUtils.isNotBlank(child.getChContent())) {
                            sbCH.append(child.getChContent() + "\r\n");
                        }

                    }
                    // 将对应多个域的音频，放到enIds
                    be.setEnIDs(enIds);
                    be.setChIDs(chIds);
                    // 将丢应多个域的英文文字拼接到一起
                    be.setEnContent(sbEN.toString());
                    be.setChContent(sbCH.toString());
                    bookEntities3.add(be);
                }
            } else {
                bookEntities3.add(bookEntity);
            }
        }
        return bookEntities3;
    }
}
