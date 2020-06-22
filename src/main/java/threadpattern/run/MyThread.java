package threadpattern.run;

/**
 * @author ChenOT
 * @date 2019-07-26
 * @see
 * @since
 */
public class MyThread extends Thread{
    @Override
    public void run(){
        synchronized (this){

            System.out.println("mythread start");

            try {
                // 只有sleep、wait、join三个方法存在时，interrupt才会起作用

//            Thread.sleep(10000);
                wait();
            } catch (InterruptedException e) {
//            e.printStackTrace();
                System.out.println("mythread interrupt");
            }
            System.out.println("mythread end");
        }
    }
}
