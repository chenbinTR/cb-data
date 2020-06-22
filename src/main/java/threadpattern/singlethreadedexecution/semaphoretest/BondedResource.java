package threadpattern.singlethreadedexecution.semaphoretest;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author ChenOT
 * @date 2019-07-30
 * @see
 * @since
 */
public class BondedResource {
    private final Semaphore semaphore;
    private final int permits;
//    private final static Random random = new Random(314159);


    public BondedResource( int permits) {
        this.semaphore = new Semaphore(permits);
        this.permits = permits;
    }

    public void use() throws InterruptedException {
        semaphore.acquire();
        try {
//            doUse();
//            Thread.sleep(random.nextInt(500));
            Thread.sleep(500);
        }finally {
            semaphore.release();
        }
    }


}
