package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ChenOT
 * @date 2019-06-28
 * @see
 * @since
 */
public class Test {
    public static void main(String[] args) {
//        Pattern pattern = Pattern.compile("^翻译([0-9a-zA-Z\\u4e00-\\u9fa5]+)");
        Pattern pattern = Pattern.compile("^你会说(\\S{1,4})吗$");
        Matcher matcher = pattern.matcher("你会说爱我吗");
        while(matcher.find()){
//            System.out.println(matcher.groupCount());
//            System.out.println(matcher.start(1));
//            System.out.println(matcher.end(1));
            System.out.println(matcher.group(1));
//            System.out.println(matcher.group(0));
//            System.out.println(matcher.group());

            System.out.println(matcher.start(1));
            System.out.println(matcher.end(1));
        }

//        String text = "翻译我喜欢你";
//        StringBuilder sb = new StringBuilder("^");
//        int start = 0;
//        for (int i=0; i<1; i++) {
//            if (!ANY_TEXT_KEY.equals(slotEntity.getKey())) {
//                continue;
//            }
//            sb.append(text.substring(start, 2));
//            sb.append("([0-9a-zA-Z\\u4e00-\\u9fa5]+)");
//            start = 6;
//        }
//        sb.append(text.substring(start));
//        System.out.println(sb.toString());
    }
}
