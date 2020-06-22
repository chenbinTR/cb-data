package threadpattern.singlethreadedexecution.semaphoretest;

/**
 * @author ChenOT
 * @date 2019-07-30
 * @see
 * @since
 */
public class Main {
    public static void main(String[] args) {
        // 设置3个资源
        BondedResource bondedResource = new BondedResource(3);
        // 10个线程使用资源
        for(int i=0; i<10;i++){
            new Thread(new UserThread(bondedResource)).start();
        }
    }
}
