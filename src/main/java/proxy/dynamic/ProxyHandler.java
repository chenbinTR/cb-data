package proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 通用代理类
 * @author ChenOT
 * @date 2020-02-06
 * @see
 * @since
 */
public class ProxyHandler implements InvocationHandler {
    /**
     * 被代理的对象
     */
    private Object proxied;

    public ProxyHandler(Object proxied ){
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("准备");
        //转调具体目标对象的方法
        Object object= method.invoke( proxied, args);
        System.out.println("完成！");
        return object;

    }
}
