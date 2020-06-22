package threadpattern.future;

import java.util.concurrent.*;

/**
 * @author ChenOT
 * @date 2019-05-20
 * @see
 * @since
 */
public class FutureTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        Future<Integer> result = executor.submit(task);
        executor.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.out.println("主线程在执行任务");
        try {
            // get 方法阻塞
            System.out.println("task运行结果" + result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }
}
