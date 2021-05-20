package regex;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则中，括号的用法示例
 *
 * @author ChenOT
 * @date 2019-06-17
 * @see
 */
public class BracketDemo {
    /**
     * 小括号()
     */
    @Test
    public void testParentheses(){
        // 与|搭配使用，表示或
        Pattern pattern = Pattern.compile("(你好|你是)(你好啊|他是)");
        Matcher matcher = pattern.matcher("你是你好啊");
        System.out.println(matcher.matches());
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

    }

    /**
     * 中括号[]
     */
    @Test
    public void testBrackets(){
        // 与|搭配使用，表示或
        Pattern pattern = Pattern.compile("[a-z0-9]");
        Matcher matcher = pattern.matcher("你是12你ab123好啊下次再见");
        while (matcher.find()) {
            System.out.println(matcher.group());
//            System.out.println(matcher.start());
//            System.out.println(matcher.end());
        }

    }

    @Test
    public void testKuohao(){
        // 是否contains 2-10个你
        Pattern pattern = Pattern.compile("你{2,10}");
        Matcher matcher = pattern.matcher("我知道你你大爷的");
        while (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.start());
            System.out.println(matcher.end());
        }

        // 中括号 []，匹配其中的任意一个，|并没有用
        pattern = Pattern.compile("[^你我他0-9]");
        matcher = pattern.matcher("看看他吧，我12345知道，你|是谁");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        // 小括号 (?:pattern)
        System.out.println("(?:pattern)");
//        Matcher matcher = Pattern.compile("要(?:你|他们)").matcher("我要你跟我说，也要他们说");
        matcher = Pattern.compile("要(你|他们)").matcher("我要你跟我说，也要他们说");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        // 小括号 (?=pattern)
        System.out.println("(?=pattern)");
        matcher = Pattern.compile("Windows(?=95|98|NT|2000)").matcher("Windows20001");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        // 小括号 (?!pattern)
        System.out.println("(?!pattern)");
        matcher = Pattern.compile("Windows(?!95|98|NT|2000)").matcher("Windows200");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        // 小括号 (?<=pattern) 与 (?=pattern) 类似，只是方向相反
        // 小伙考 (?<!pattern) 与 (?!pattern) 类似，只是方向相反

        // |
        System.out.println("这次是 | ");
        matcher = Pattern.compile("95|98|NT|2000").matcher("Windows2000");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
