package proxy.dynamic;

/**
 * @author ChenOT
 * @date 2020-02-06
 * @see
 * @since
 */
public class RealSubject implements Subject {
    @Override
    public void show() {
        System.out.println("show幕后黑手");
    }

    @Override
    public void share() {
        System.out.println("share幕后黑手");
    }
}
