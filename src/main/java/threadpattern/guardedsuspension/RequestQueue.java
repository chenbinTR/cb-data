package threadpattern.guardedsuspension;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 保存任务列表的共享队列
 */
public class RequestQueue {
    /**
     * 使用LinkedBlockingQueue（线程安全的队列）实现
     */
    private final BlockingQueue<Request> queue = new LinkedBlockingQueue<Request>();
    public Request getRequest() {
        Request req = null;
        try {
             //若que为空，则调用take方法会进行wait
            req = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return req;
    }
    public void putRequest(Request request) {
        try {
             //put方法会触发notifyAll
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 非线程安全的queue
     */
//    private final Queue<Request> queue = new LinkedList<Request>();
//    public synchronized Request getRequest() {
//        // 获取头元素，但不删除该元素
//        // 守护条件
//        while (queue.peek() == null) {
//            try {
//                // 执行wait方法，必须获取“锁”，等同于this.wait，进入等待队列的是 this
//                wait();
//                // notifyAll之后，从这里开始往下执行
//            } catch (InterruptedException e) {
//            }
//        }
//        // 移除头元素
//        return queue.remove();
//    }
//    public synchronized void putRequest(Request request) {
//        // 添加元素到队尾
//        queue.offer(request);
//        // 执行notifyAll方法，必须获取“锁”
//        notifyAll();
//    }
}
