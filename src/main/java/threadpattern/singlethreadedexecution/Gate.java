package threadpattern.singlethreadedexecution;

import java.util.concurrent.atomic.AtomicInteger;

public class Gate {
    private int counter = 0;
//    private final AtomicInteger counter = new AtomicInteger(0);
    private String name = "Nobody";
    private String address = "Nowhere";

    /**
     * 明确synchronized 保护的是什么
     * 此类中，保护的是 counter name address 三个变量
     *
     * @param name
     * @param address
     */
    public synchronized void pass(String name, String address) {
        this.counter++;
//        counter.getAndIncrement();
        System.out.println("counter: "+counter);
        this.name = name;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        this.address = address;
        if (name.charAt(0) != address.charAt(0)) {
            System.out.println("***** BROKEN ***** " + toString());
        }
    }

    @Override
    public String toString() {
        return "No." + counter + ": " + name + ", " + address;
    }
}
