package threadpattern.producerconsumer;

/**
 * @author ChenOT
 * @date 2019-08-05
 * @see
 * @since
 */
public interface TableInterface<T> {
    /**
     * 添加
     * @param t
     */
    void put(T t) throws InterruptedException;

    /**
     * 取出
     * @return
     */
    T take() throws InterruptedException;
}
