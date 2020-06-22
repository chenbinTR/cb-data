package annotation;

import java.lang.annotation.*;


/**
 * 元注解
 * 1.@Target,说明了Annotation所修饰的对象范围（作用范围/对象）
 * 取值(ElementType)有：

 　　　　1.CONSTRUCTOR:用于描述构造器
 　　　　2.FIELD:只能修饰字段(属性),包括枚举常量
 　　　　3.LOCAL_VARIABLE:用于描述局部变量
 　　　　4.METHOD:用于描述方法
 　　　　5.PACKAGE:用于描述包
 　　　　6.PARAMETER:用于描述参数
 　　　　7.TYPE:用于描述类、接口(包括注解类型) 或enum声明


 　　2.@Retention,定义了该Annotation被保留的时间长短（生命周期）
        取值（RetentionPoicy）有：

 　　　　1.SOURCE:在源文件中有效（即源文件保留）
 　　　　2.CLASS:在class文件中有效（即class保留）
 　　　　3.RUNTIME:在运行时有效（即运行时保留）

 　　3.@Documented,用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。Documented是一个标记注解，没有成员。

 　　4.@Inherited
 */

@Target(ElementType.FIELD) //
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitColor {
    /**
     * 颜色枚举
     * @author peida
     */
    public enum Color{ BULE,RED,GREEN};
    
    /**
     * 颜色属性
     * @return
     */
    Color fruitColor() default Color.GREEN;

}