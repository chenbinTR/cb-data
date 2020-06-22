package proxy.dynamic;

import java.lang.reflect.Proxy;

/**
 * @author ChenOT
 * @date 2020-02-06
 * @see
 * @since
 */
public class DynamicProxyTest {
    public static void main( String args[] ){
        // 测试subject代理
        Subject realSubject = new RealSubject();
        /*
            //一个ClassLoader对象，定义了由哪个ClassLoader对象来对生成的代理对象进行加载
            Subject.class.getClassLoader(),
            //一个Interface对象的数组，表示的是我将要给我需要代理的对象提供一组什么接口，如果我提供了一组接口给它，那么这个代理对象就宣称实现了该接口(多态)
            new Class[]{Subject.class},
            //一个InvocationHandler对象，表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个InvocationHandler
            new SubjectProxyHandler(real));
        */
        Subject proxySubject =(Subject) Proxy.newProxyInstance(
                Subject.class.getClassLoader(),
                new Class[]{Subject.class},
                new ProxyHandler(realSubject));
        proxySubject.share();

        // 测试person代理
        Person realPerson = new RealPerson();
        Person proxyPerson = (Person)Proxy.newProxyInstance(
                Person.class.getClassLoader(),
                new Class[]{Person.class},
                new ProxyHandler(realPerson));
        proxyPerson.sayHello();
    }

}
