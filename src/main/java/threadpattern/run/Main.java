package threadpattern.run;

/**
 * @author ChenOT
 * @date 2019-08-05
 * @see
 * @since
 */
public class Main {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
//        myThread.interrupt();
        System.out.println(myThread.isInterrupted());
    }
}
