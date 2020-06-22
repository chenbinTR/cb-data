package threadpattern;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangsx
 * @Title: com.turing.shield
 * @Description: 线程池容器的设计
 * @date 2018/6/20
 */
public final class ThreadContainer {


    public static volatile long TIME_OUT = 40L;

    private static ExecutorService service = new ThreadPoolExecutor(
            300, 300,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());

    public static ExecutorService getExecutor() {
        return service;
    }
}
