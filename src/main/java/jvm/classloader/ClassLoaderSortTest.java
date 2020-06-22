package jvm.classloader;

/**
 * @author ChenOT
 * @date 2019-10-09
 * @see
 * @since
 */

class Grandpa {
    static {
        System.out.println("爷爷初始化");
    }
}

class Father extends Grandpa {
    static {
        System.out.println("爸爸初始化");
    }

    /**
     * final 是关键
     */
//    public static final int factor = 25;
    public static final int factor = 25;

    public Father() {
        System.out.println("我是爸爸~");
    }
}

class Son extends Father {
    public static int factor2 = 25;

    static {
        System.out.println("儿子初始化");
    }

    public Son() {
        System.out.println("我是儿子~");
    }
}

public class ClassLoaderSortTest {
    //    public class InitializationDemo {
    public static void main(String[] args) {

        System.out.println("爸爸的岁数:" + Son.factor);    //入口
    }
}
