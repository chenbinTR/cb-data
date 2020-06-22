package threadpattern.singlethreadedexecution.semaphoretest;

/**
 * @author ChenOT
 * @date 2019-07-30
 * @see
 * @since
 */
public class UserThread implements Runnable {
//    private final static Random
    private final BondedResource bondedResource;

    public UserThread(BondedResource bondedResource) {
        this.bondedResource = bondedResource;
    }


    @Override
    public void run() {
        try{
            while (true){
                bondedResource.use();
                Thread.sleep(1000);
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
