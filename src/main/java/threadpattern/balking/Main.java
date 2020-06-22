package threadpattern.balking;

public class Main {
    public static void main(String[] args) {
        Data data = new Data("Q:\\data.txt", "(empty)");
        new ChangerThread("修改线程", data).start();
        new SaverThread("保存线程", data).start();
    }
}
