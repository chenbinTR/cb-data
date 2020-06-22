package threadpattern.producerconsumer;

/**
 * 通道抽象接口
 * @author ChenOT
 * @date 2019-09-04
 * @see
 * @since
 */
public interface Channel<P> {
    /**
     *往通道中存入一个产品
     * @param product
     * @throws InterruptedException
     *
     */
    void put(P product) throws InterruptedException;

    /**
     * 从通道中取出一个产品
     * @return
     * @throws InterruptedException
     */
    P take() throws InterruptedException;
}
