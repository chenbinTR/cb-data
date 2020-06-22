package threadpattern.threadpermessage;

public class Helper {
    public void handle(int count, char c) {
        for (int i = 0; i < count; i++) {
            slowly();
            System.out.print(c);
        }
    }
    private void slowly() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}
