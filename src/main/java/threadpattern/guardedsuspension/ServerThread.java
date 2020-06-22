package threadpattern.guardedsuspension;
/**
 * 负责 get 元素的线程
 */
public class ServerThread extends Thread {
    private final RequestQueue requestQueue;
    public ServerThread(RequestQueue requestQueue, String name) {
        super(name);
        this.requestQueue = requestQueue;
    }
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Request request = requestQueue.getRequest();
            System.out.println(Thread.currentThread().getName() + " handles  " + request);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }
}
