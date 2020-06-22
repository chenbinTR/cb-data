package threadpattern.singlethreadedexecution.deadlock;

/**
 * @author ChenOT
 * @date 2019-07-31
 * @see
 * @since
 */
public class EaterThread extends Thread{
    private final Tool leftHand;
    private final Tool rightHand;
    private final String name;

    public EaterThread(Tool leftHand, Tool rightHand, String name) {
        System.out.println(Thread.currentThread().getName());
        this.leftHand = leftHand;
        this.rightHand = rightHand;
        this.name = name;
    }


    @Override
    public void run(){
        while(true){
            eat();
        }
    }

    private void eat(){
        synchronized (leftHand){
//            System.out.println(name + "持有"+leftHand+" (left).");
            synchronized (rightHand){
//                System.out.println(name + "持有"+rightHand+" (right). ");
//                System.out.println(name + "正在吃。");
//                System.out.println(name + "放下"+rightHand+" (right). ");
            }
//            System.out.println(name+"放下"+leftHand+"(left).");
        }
    }
}
