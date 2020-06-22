package threadpattern.threadpermessage;

public class Host {
    private final Helper helper = new Helper();
    public void request(final int count, final char c) {
        new Thread() {
            public void run() {
                helper.handle(count, c);
            }
        }.start();
    }
}
