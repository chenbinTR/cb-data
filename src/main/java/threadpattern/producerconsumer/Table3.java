package threadpattern.producerconsumer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 直接使用 blockingqueue创建的table类
 * @author ChenOT
 * @date 2019-08-05
 * @see
 * @since
 */
public class Table3 extends LinkedBlockingQueue<String> implements TableInterface<String>{
    public Table3(int count){
        super(count);
    }
    @Override
    public void put(String cake) throws InterruptedException {
        System.out.println("start put");
        super.put(cake);
        System.out.println("put success");
    }

    @Override
    public String take() throws InterruptedException {
        System.out.println("start eat");
        String cake = super.take();
        System.out.println("eat success");
        return cake;
    }
}
