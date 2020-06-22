package threadpattern.workthread;

/**
 * workthrea模式，就是线程池模式
 */
public class Main {
    public static void main(String[] args) {
        // 工作线程5个
        Channel channel = new Channel(5);
        channel.startWorkers();

        new ClientThread("张三", channel).start();
        new ClientThread("李四", channel).start();
        new ClientThread("王二", channel).start();
    }
}
