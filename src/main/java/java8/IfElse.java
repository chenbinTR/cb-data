package java8;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 优化（美化）if-else的编码方式
 * @author ChenOT
 * @date 2020-05-07
 * @see
 * @since
 */
public class IfElse {
    /**
     * 利用Function接口代替if else
     */
    @Test
    public void method1() {
        Map<Integer, Function> actionMappings = new HashMap<>(3);
        Function<Integer, Integer> function = s -> ++s;
        // When init
        actionMappings.put(0, function);
        actionMappings.put(1, function);
        actionMappings.put(2, function);
        // 省略 null 判断
        System.out.println(actionMappings.get(0).apply(1000));
    }

    /**
     * 利用职责链模式
     */
    @Test
    public void method2(){

    }

    /**
     * 注解驱动
     */
    @Test
    public void method3(){

    }
}
