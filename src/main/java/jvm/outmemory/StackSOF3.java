package jvm.outmemory;

/**
 * 场景三：大量创建线程
 * @author ChenOT
 * @date 2020-08-05
 * @see
 * @since
 */
public class StackSOF3 {
    private void dontStop(){
        while (true){}
    }

    public void stackLeakByThread() {
        while(true){
            new Thread(()->dontStop()).start();
        }
    }

    public static void main(String[] args) {
        StackSOF3 stackSOF3 = new StackSOF3();
        stackSOF3.stackLeakByThread();
    }
}
