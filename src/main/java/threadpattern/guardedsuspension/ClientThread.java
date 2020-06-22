package threadpattern.guardedsuspension;

/**
 * 负责 put 元素的线程
 */
public class ClientThread extends Thread {
    private final RequestQueue requestQueue;
    public ClientThread(RequestQueue requestQueue, String name) {
        super(name);
        this.requestQueue = requestQueue;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Request request = new Request("No." + i);
            System.out.println(Thread.currentThread().getName() + " requests " + request);
            requestQueue.putRequest(request);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }
}
