package threadpattern.singlethreadedexecution.deadlock;

/**
 * @author ChenOT
 * @date 2019-07-31
 * @see
 * @since
 */
public class Main {
    public static void main(String[] args) {
        // 创建勺子和叉子
        Tool spoon = new Tool("spoon");
        Tool fork = new Tool("fork");

        new EaterThread(spoon, fork, "老大").start();
        // 不会死锁，因为获取锁的顺序是一样的
        new EaterThread(spoon, fork, "老二").start();
        // 会死锁，两个线程获取锁的顺序是不同的
//        new EaterThread(fork, spoon, "老二").start();


    }
}
