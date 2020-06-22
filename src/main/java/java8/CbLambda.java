package java8;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author ChenOT
 * @date 2019-05-29
 * @see
 * @since
 */
public class CbLambda {
    private String[] atp = {"Rafael Nadal", "Novak Djokovic",
            "Stanislas Wawrinka",
            "David Ferrer", "Roger Federer",
            "Andy Murray", "Tomas Berdych",
            "Juan Martin Del Potro"};

    /**
     * 普通用法
     */
    @Test
    public void testNormal() {
        List<String> players = Arrays.asList(atp);
        System.out.println("**************************普通循环");
        for (String player : players) {
            System.out.println(player);
        }
        System.out.println("**************************lambda循环");
        players.forEach(player -> System.out.println(player));
        System.out.println("**************************双冒号循环");
        players.forEach(System.out::println);
    }

    /**
     * 匿名内部类
     */
    @Test
    public void comparatorTest() {
        // 匿名内部类
        Arrays.sort(atp, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return (s1.compareTo(s2));
            }
        });
        System.out.println(JSON.toJSONString(atp));
        // 使用lambda进行逆序
        Comparator<String> comparator = (String s1, String s2) -> (s2.compareTo(s1));
        Arrays.sort(atp, comparator);
        System.out.println(JSON.toJSONString(atp));
        // 另外一种写法
        Arrays.sort(atp, (String s1, String s2) -> (s2.compareTo(s1)));
    }

    /**
     * runnable中使用lambda表达式
     */
    public void runnableTest() {
        // 1.1使用匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world !");
            }
        }).start();

        // 1.2使用 lambda expression
        new Thread(() -> System.out.println("Hello world !")).start();

        // 2.1使用匿名内部类
        Runnable race1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world !");
            }
        };

        // 2.2使用 lambda expression
        Runnable race2 = () -> System.out.println("Hello world !");

        // 直接调用 run 方法(没开新线程哦!)
        race1.run();
        race2.run();
    }
}
