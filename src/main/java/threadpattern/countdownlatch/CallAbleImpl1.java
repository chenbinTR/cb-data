package threadpattern.countdownlatch;

import java.util.concurrent.Callable;

/**
 * 有返回值的任务
 * @author ChenOT
 * @Date 2021/4/15
 */
public class CallAbleImpl1 implements Callable<String> {
    private String param;

    CallAbleImpl1(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"开始执行");
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName()+"执行完成");
        return param+"1";
    }
}
