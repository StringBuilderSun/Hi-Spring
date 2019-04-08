package spring.road.core.type;

/**
 * 类的元数据属性
 * User: lijinpeng
 * Created by Shanghai on 2019/4/8.
 */
public interface ClassMetadata {
    /**
     * 得到类名
     * @return
     */
    String getClassName();
    /**
     * 类是否不可以被继承
     * @return
     */
    boolean isFinal();

    /**
     * 是否是一个抽象类
     * @return
     */
    boolean isAbstract();

    /**
     * 是否是一个接口
     * @return
     */
    boolean isInterface();

    /**
     * 获取接口集合
     * @return
     */
    String[] getInterfaceNames();

    /**
     * 获取父类名字
     * @return
     */
    String getSuperClassName();


}
