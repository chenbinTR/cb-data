package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ChenOT
 * @date 2019-06-04
 * @see
 * @since
 */
public class RegexDemo {
    public static void main(String[] args) {
        // Pattern.matches() ,全匹配判断
        System.out.println(Pattern.matches("\\d+", "2223"));
        System.out.println(Pattern.matches("\\d+", "2223ab"));

        Pattern pattern = Pattern.compile("\\d+");
        // 返回正则表达式本身
        System.out.println("pattern.pattern(): " + pattern.pattern());

        //Matcher 的用法
        Matcher matcher = pattern.matcher("22ba33");
        // Matcher.matches() 全匹配判断
        System.out.println("matcher.matches(): " + matcher.matches());
        // Matcher.lookingAt() 起始判断（是否以正则开始）
        System.out.println("matcher.lookingAt(): " + matcher.lookingAt());
        // Matcher.find() 查找匹配，可以是任意位置，类似contains
        System.out.println("matcher.find(): " + matcher.find());

        // Matcher.find(), Matcher.lookingAt(), Matcher.group() 用在  Matcher.matches(), Matcher.lookingAt(), Matcher.find() 之后
        // 紧接上面的matcher.find(), 如果find为false，则会异常
        System.out.println(matcher.start());
        System.out.println(matcher.end());
        System.out.println(matcher.group());

        Pattern p = Pattern.compile("([a-z]+)(\\d+)");
        Matcher m = p.matcher("aaa2223bb");
        //匹配aaa2223
        m.find();
        //返回2,因为有2组
        m.groupCount();
        //返回0 返回第一组匹配到的子字符串在字符串中的索引号
        m.start(1);
        //返回3
        m.start(2);
        //返回3 返回第一组匹配到的子字符串的最后一个字符在字符串中的索引位置.
        m.end(1);
        //返回7
        m.end(2);
        //返回aaa,返回第一组匹配到的子字符串
        m.group(1);
        //返回2223,返回第二组匹配到的子字符串
        m.group(2);


        Pattern p1 = Pattern.compile("\\d+");
        Matcher m1 = p1.matcher("我的QQ是:456456 我的电话是:0532214 我的邮箱是:aaa123@aaa.com");
        while (m1.find()) {
            System.out.println(m1.group());
            System.out.print("start:" + m.start());
            System.out.println(" end:" + m.end());
        }

        // 按指定模式在字符串查找
        String line = "This 1 order 2 was placed for QT3000! OK?";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile("(\\D*)(\\d+)(.*)");
        // 现在创建 matcher 对象
        Matcher h = r.matcher(line);
        while (h.find( )) {
            System.out.println("Found value: " + h.group(0) );
            System.out.println("Found value: " + h.group(1) );
            System.out.println("Found value: " + h.group(2) );
            System.out.println("Found value: " + h.group(3) );
        }
    }
}
