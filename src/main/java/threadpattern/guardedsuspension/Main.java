package threadpattern.guardedsuspension;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        RequestQueue requestQueue = new RequestQueue();
        new ClientThread(requestQueue, "大").start();
        new ServerThread(requestQueue, "二").start();
    }
}
