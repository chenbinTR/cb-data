package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则中，括号的用法示例
 * @author ChenOT
 * @date 2019-06-17
 * @see
 * @since
 */
public class BracketDemo {
    public static void main(String[] args) {
        // 大括号 {}
        Pattern patternBig = Pattern.compile("你{3,4}");
        Matcher matcherBig = patternBig.matcher("我知道你你大爷的");
        while (matcherBig.find()){
            System.out.println(matcherBig.group());
            System.out.println(matcherBig.start());
            System.out.println(matcherBig.end());
        }
        // 中括号 []，匹配其中的任意一个，|并没有用
        Pattern patternMiddle = Pattern.compile("[^你我他0-9]");
        Matcher matcherMidele = patternMiddle.matcher("看看他吧，我12345知道，你|是谁");
        while (matcherMidele.find()){
            System.out.println(matcherMidele.group());
        }

        // 小括号 (?:pattern)
        System.out.println("(?:pattern)");
        Matcher matcher = Pattern.compile("我要(?:你|他们)").matcher("我要你跟我说");
        while(matcher.find()){
            System.out.println(matcher.group());
        }

        // 小括号 (?=pattern)
        System.out.println("(?=pattern)");
        matcher = Pattern.compile("Windows(?=95|98|NT|2000)").matcher("Windows2000");
        while(matcher.find()){
            System.out.println(matcher.group());
        }

        // 小括号 (?!pattern)
        System.out.println("(?!pattern)");
        matcher = Pattern.compile("Windows(?!95|98|NT|2000)").matcher("Windows200");
        while(matcher.find()){
            System.out.println(matcher.group());
        }

        // 小括号 (?<=pattern) 与 (?=pattern) 类似，只是方向相反
        // 小伙考 (?<!pattern) 与 (?!pattern) 类似，只是方向相反

        // |
        System.out.println("这次是 | ");
        matcher = Pattern.compile("95|98|NT|2000").matcher("Windows2000");
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }
}
