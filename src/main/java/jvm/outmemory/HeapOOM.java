package jvm.outmemory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenOT
 * @date 2020-08-05
 * @see
 * @since
 */
public class HeapOOM {
    static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while(true){
            list.add(new OOMObject());
        }
    }
}
