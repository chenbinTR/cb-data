package generics;

/**
 * 泛型——本质上就是模版
 * 泛型类
 * @author ChenOT
 * @date 2019-08-05
 * @see
 * @since
 */
public class GeneticClassTest<T> {
    private T t;

    public void add(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public static void main(String[] args) {
        GeneticClassTest<Integer> integerBox = new GeneticClassTest<Integer>();
        GeneticClassTest<String> stringBox = new GeneticClassTest<String>();

        integerBox.add(new Integer(10));
        stringBox.add(new String("菜鸟教程"));

        System.out.printf("整型值为 :%d\n\n", integerBox.get());
        System.out.printf("字符串为 :%s\n", stringBox.get());
    }
}
