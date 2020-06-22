package threadpattern.immutable;

/**
 * immutable类，要点
 * 1、类为final
 * 2、类创建完成，属性也就固定
 * 3、不可修改属性
 * @author ChenOT
 * @date 2019-07-31
 * @see
 * @since
 */
public final class Person {
    // 注意stringbuffer 并不是immutable的
    private final StringBuffer name;
    // string是immutable的
    private final String sex;

    public Person(String name, String sex) {
        this.name = new StringBuffer(name);
        this.sex = sex;
    }

    public StringBuffer getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
