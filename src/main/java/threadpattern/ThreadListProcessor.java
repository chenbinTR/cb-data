package threadpattern;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author ChenOT
 * @date 2020-01-10
 * @see
 * @since
 */
public class ThreadListProcessor {
    public static void main(String[] args) {
        for(int i=0; i<3; i++){
            test();
        }
    }
    private static void test(){
        long time = System.currentTimeMillis();
        List<Callable<String>> taskList = new ArrayList<>();
        taskList.add(() -> method());
        taskList.add(() -> method2());
//        System.out.println(System.currentTimeMillis() - time);
        try {
            List<Future<String>> futureList =  ThreadContainer.getExecutor().invokeAll(taskList, 40, TimeUnit.MILLISECONDS);
            if(CollectionUtils.isNotEmpty(futureList)){
                for(Future<String> future:futureList){
                    try {
                        System.out.println(future.get());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - time);
    }
    private static String method(){
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "1";
    }
    private static String method2(){
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "2";
    }
}
