package threadpattern.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 主线程等待子线执行完成
 */
public class CountdownLatchTest1 {
    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        System.out.println(getResult("dddd"));
        System.out.println(System.currentTimeMillis() - time1);
    }

    /**
     * 主线程等待子线程执行完成
     * @param args
     */
    private static void test(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        final CountDownLatch latch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = () -> {
                try {
                    System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
                    Thread.sleep(2000);
                    System.out.println("子线程" + Thread.currentThread().getName() + "执行完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();//当前线程调用此方法，则计数减一
                }
            };
            service.execute(runnable);
        }

        try {
            System.out.println("主线程" + Thread.currentThread().getName() + "等待子线程执行完成...");
            latch.await();//阻塞当前线程，直到计数器的值为0
            System.out.println("主线程" + Thread.currentThread().getName() + "开始执行...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 有返回值的，主线程等待子线程，并对所有结果统一处理
     *
     * @param param
     * @return
     */
    private static String getResult(String param) {
        ExecutorService service = Executors.newFixedThreadPool(1);
//        final CountDownLatch latch = new CountDownLatch(2);
        Future<String> future1 = service.submit(new CallAbleImpl1("ok"));
        Future<String> future2 = service.submit(new CallAbleImpl1("ok"));
//        for (int i = 0; i < 3; i++) {
//            Runnable runnable = () -> {
//                try {
//                    System.out.println("子线程" + Thread.currentThread().getName() + "开始执行");
//                    Thread.sleep(2000);
//                    System.out.println("子线程" + Thread.currentThread().getName() + "执行完成");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    latch.countDown();//当前线程调用此方法，则计数减一
//                }
//            };
//            service.execute(runnable);
//        }

        try {
            System.out.println("主线程" + Thread.currentThread().getName() + "等待子线程执行完成...");
//            latch.await();//阻塞当前线程，直到计数器的值为0
            String result = future1.get() + future2.get();
            System.out.println("主线程" + Thread.currentThread().getName() + "开始执行...");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}