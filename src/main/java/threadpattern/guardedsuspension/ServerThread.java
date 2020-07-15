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

    @Override
    public void run() {
//        for (int i = 0; i < 10000; i++) {
        while (true) {
            Request request = requestQueue.getRequest();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " handles  " + request);
        }
    }
}
