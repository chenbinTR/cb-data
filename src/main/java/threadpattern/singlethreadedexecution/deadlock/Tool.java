package threadpattern.singlethreadedexecution.deadlock;

/**
 * 代表“勺子”“叉子”的工具类
 * @author ChenOT
 * @date 2019-07-31
 * @see
 * @since
 */
public class Tool {
    private final String name;

    public Tool(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "name='" + name + '\'' +
                '}';
    }
}
