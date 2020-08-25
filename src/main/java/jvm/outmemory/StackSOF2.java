package jvm.outmemory;

/**
 * 场景一：线程请求的栈深度大于虚拟机所允许的最大深度
 * @author ChenOT
 * @date 2020-08-05
 * @see
 * @since
 */
public class StackSOF2 {
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        StackSOF2 stackOOM = new StackSOF2();
        try {
            stackOOM.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length: " + stackOOM.stackLength);
            throw e;
        }
    }
}
