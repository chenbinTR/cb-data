package threadpattern.future;

import java.util.concurrent.Callable;

/**
 * @author ChenOT
 * @date 2019-07-09
 * @see
 * @since
 */
public class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        Thread.sleep(5000);
        int sum = 0;
        for (int i = 0; i < 100; i++)
            sum += i;
        return sum;
    }
}
