package java8;

import io.vavr.control.Try;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.*;

/**
 * @author ChenOT
 * @date 2019-07-11
 * @see
 * @since
 */
public class SupplierTest {
    @Test
    public void testPredicate(){
        Predicate<String> p = s->s.equals("test");
        Predicate<String> g = s->s.startsWith("t");

        // 反向处理
        Assert.assertFalse(p.negate().test("test"));

        // and，同逻辑与
        Assert.assertTrue(p.and(g).test("test"));

        // or，同逻辑或
        Assert.assertTrue(p.or(g).test("ta"));

    }
    @Test
    public void testFunction() {
        Function<Integer, Integer> function = s -> ++s;
        Function<Integer, Integer> function1 = t -> t * 2;

        // 使用function1的输出，作为function的输入
        System.out.println(function.compose(function1).apply(2));

        // 使用function的输出作为function1的输入
        System.out.println(function.andThen(function1).apply(2));

        // identity 不做任何处理，输出输入
        System.out.println(Function.identity().apply(123));
    }

    @Test
    public void testSupplier() {
        Supplier<Person> personSupplier = () -> new Person();
        // 每执行一次get方法，就重新执行一次 get方法的实现（这不是废话吗）
        System.out.println(personSupplier.get());
        System.out.println(personSupplier.get());
        System.out.println(personSupplier.get());

        IntSupplier intSupplier = () -> {
            int i = 1 + 2;
            return i;
        };
        System.out.println(intSupplier.getAsInt());
        System.out.println(intSupplier.getAsInt());
        System.out.println(intSupplier.getAsInt());

        Try<Integer> result = Try.of(() -> 1 / 0).recover(e -> 1);
        System.out.println(result);

        Supplier<String> stringSupplier = ()->{
            System.out.println(111);
            throw new RuntimeException();
        };
        String result2 = Try.ofSupplier(stringSupplier).recover(e -> null).get();
        System.out.println(result2);
    }

    @Test
    public void testConsumer() {
        Consumer<Integer> consumer = x -> {
            int num = x * 2;
            System.out.println(num);
        };
        Consumer<Integer> consumer1 = x -> {
            int num = x * 3;
            System.out.println(num);
        };
        Consumer<Integer> consumer2 = x -> {
            int num = x * 4;
            System.out.println(num);
        };

        consumer.andThen(consumer1).andThen(consumer2).accept(10);
    }
}
