package threadpattern.producerconsumer;

/**
 * 生产者-消费者模式
 * 三位生成者 makethread，制作蛋糕string，并将其放到桌子上 table
 * 三位消费者 eaterthread，从桌子table上取蛋糕string
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 可以放置3个蛋糕的桌子
//        Table table = new Table(3);
//        Table1 table = new Table1(3);
        Table3 table = new Table3(3);
        // 创建三个生产蛋糕的maker
        new MakerThread("MakerThread-1", table, 31415).start();
        new MakerThread("MakerThread-2", table, 92653).start();
        new MakerThread("MakerThread-3", table, 58979).start();
        // 创建三个吃蛋糕的eater
        new EaterThread("EaterThread-1", table, 32384).start();
        new EaterThread("EaterThread-2", table, 62643).start();
        new EaterThread("EaterThread-3", table, 38327).start();
    }
}
