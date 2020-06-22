package threadpattern.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 用queue 实现的table
 */
public class Table implements TableInterface<String>{
    private final BlockingQueue<String> queue;
    public Table(int count) {
        this.queue = new LinkedBlockingQueue<String>(count);
    }
    // 放置蛋糕
    @Override
    public synchronized void put(String cake) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " puts ……" + queue.remainingCapacity());
        while (queue.remainingCapacity()<=0) {
            wait();
        }
        System.out.println(Thread.currentThread().getName() + " puts success " + cake);
        queue.put(cake);
        notifyAll();
    }
    // 取蛋糕
    @Override
    public synchronized String take() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " takes ……");
        while (queue.isEmpty()) {
            wait();
        }
        String cake = queue.take();
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " takes success " + cake);
        return cake;
    }
}
