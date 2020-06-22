package threadpattern.producerconsumer;

import java.util.Random;

public class EaterThread extends Thread {
    private final Random random;
    private final TableInterface<String> table;
    public EaterThread(String name, TableInterface<String> table, long seed) {
        super(name);
        this.table = table;
        this.random = new Random(seed);
    }
    @Override
    public void run() {
        try {
            while (true) {
                table.take();
//                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
        }
    }
}
