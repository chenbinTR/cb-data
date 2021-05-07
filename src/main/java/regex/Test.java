package regex;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ChenOT
 * @date 2019-06-28
 * @see
 * @since
 */
public class Test {
    private static final Pattern TODAY = Pattern.compile("(?:大?前天|昨天|今天|明天|明|大?后天)");
    private static final Pattern CN_CHAR_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    public static void main(String[] args) {

        splitNotes("寥（liáo）落：寂寞冷落。行宫：皇帝在京城之外的宫殿。这里指当时东都洛阳的皇帝行宫上阳宫。宫花：行宫里的花。白头宫女：据白居易《上阳白发人》，一些宫女天宝末年被“潜配”到上阳宫，在这冷宫里一闭四十多年，成了白发宫人。说：谈论。玄宗：指唐玄宗。");
    }

    public static void biaodian(){
        String stri = "ss&*(,.~1如果@&(^-自己!!知道`什`么#是$苦%……Z，&那*()么一-=定——+告诉::;\"'/?.,><[]{}\\||别人什么是甜。";
        System.out.println(stri);

        String stri1 = stri.replaceAll("\\p{Punct}", "");//不能完全清除标点
        System.out.println(stri1);

        String stri2 = stri.replaceAll("\\pP", "");//完全清除标点
        System.out.println(stri2);

        String stri3 = stri.replaceAll("\\p{P}", "");//同上,一样的功能
        System.out.println(stri3);

        String stri4 = stri.replaceAll("[\\pP\\p{Punct}]", "");//清除所有符号,只留下字母 数字 汉字 共3类.
        System.out.println(stri4);

        String str5 = stri.replaceAll("[^\u4e00-\u9fa5]", "");
        System.out.println(str5);

        Matcher matcher = TODAY.matcher("我知道今天会，明早下雨");
        while (matcher.find()) {
            System.out.println(matcher.groupCount());
//            System.out.println(matcher.start(1));
//            System.out.println(matcher.end(1));
            System.out.println(matcher.group());
//            System.out.println(matcher.group(1));
//            System.out.println(matcher.group());
        }
    }

    public static List<String> splitNotes(String text) {
//        Pattern p1 = Pattern.compile("[\u4e00-\u9fa5]（.*?）[\u4e00-\u9fa5]：");
        Pattern p1 = Pattern.compile("[^。]+：");
        Matcher m1 = p1.matcher(text);
        List<String> notes = new ArrayList<>();
        int start = 0;
        int end = 0;
        while (m1.find()) {
            end = m1.start();
            if(start == end){
                continue;
            }
            notes.add(text.substring(start, end));
            start = end;
        }

        if(end == 0){
            notes.add(text);
        }else{
            notes.add(text.substring(end));
        }
        notes.forEach(System.out::println);
        return notes;
    }
}
