package jvm.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ChenOT
 * @date 2019-10-09
 * @see
 * @since
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception{
        // 自定义类加载器
        ClassLoader loader=new ClassLoader() {
            @Override
            public Class<?> loadClass(String name)throws ClassNotFoundException{
                try{
                    String filename=name.substring(name.lastIndexOf(".")+1)+".class";
                    InputStream is=getClass().getResourceAsStream(filename);
                    if(is==null){
                        return super.loadClass(name);
                    }
                    byte[] b=new byte[is.available()];
                    is.read(b);
                    return defineClass(name,b,0,b.length);
                }catch(IOException e){
                    throw new ClassNotFoundException(name);
                }
            }
        };
        Object obj=loader.loadClass("jvm.classloader.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj.getClass().getClassLoader());
        System.out.println(ClassLoaderTest.class.getClassLoader());
        System.out.println(obj instanceof jvm.classloader.ClassLoaderTest);
    }
}
