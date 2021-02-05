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
    private static final Pattern TODAY = Pattern.compile("(?:大?前天|昨天|今天|明天|明|大?后天)");

    public static void main(String[] args) {
        Matcher matcher = TODAY.matcher("我知道今天会，明早下雨");
        while(matcher.find()){
            System.out.println(matcher.groupCount());
//            System.out.println(matcher.start(1));
//            System.out.println(matcher.end(1));
            System.out.println(matcher.group());
//            System.out.println(matcher.group(1));
//            System.out.println(matcher.group());
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
