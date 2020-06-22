package proxy.dynamic;

/**
 * @author ChenOT
 * @date 2020-02-06
 * @see
 * @since
 */
public class RealPerson implements Person {
    @Override
    public void sayHello() {
        System.out.println("sayHello我是真人");
    }
}
