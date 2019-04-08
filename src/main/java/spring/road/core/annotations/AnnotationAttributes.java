package spring.road.core.annotations;

import spring.road.beans.utils.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * 存储一个类上的所有注解类所有属性
 * key 为注解名 value是 注解对应的所有配置的属性
 * 继承于linkHashMap 仅仅是为了扩展一些获取指定类型值的方法
 * User: lijinpeng
 * Created by Shanghai on 2019/4/8.
 */
public class AnnotationAttributes extends LinkedHashMap<String,Object>{

    public AnnotationAttributes() {
    }

    /**
     * 创建一个指定初始容量的hashmap
     * @param initialCapacity
     */
    public AnnotationAttributes(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * 创建一个hashmap,并将map放入
     * @param map
     */
    public AnnotationAttributes(Map<String, Object> map) {
        super(map);
    }

    /**
     * 根据属性名 获取属性值String
     * @param attributeName
     * @return
     */
    public String getString(String attributeName) {
        return doGet(attributeName, String.class);
    }

    /**
     * 根据属性名 获取string[] 数组的属性值
     * @param attributeName
     * @return
     */
    public String[] getStringArray(String attributeName) {
        return doGet(attributeName, String[].class);
    }

    /**
     * 根据属性名 获取boolean类型的值
     * @param attributeName
     * @return
     */
    public boolean getBoolean(String attributeName) {
        return doGet(attributeName, Boolean.class);
    }

    /**
     * 根据属性名 获取int的属性值
     * @param attributeName
     * @param <N>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <N extends Number> N getNumber(String attributeName) {
        return (N) doGet(attributeName, Integer.class);
    }

    /**
     * 根据属性名获取一个枚举对象
     * @param attributeName
     * @param <E>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <E extends Enum<?>> E getEnum(String attributeName) {
        return (E) doGet(attributeName, Enum.class);
    }

    /**
     * 根据属性名获取一个类对象
     * @param attributeName
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Class<? extends T> getClass(String attributeName) {
        return doGet(attributeName, Class.class);
    }

    /**
     * 通过属性 名 获取一个类数组对象
     * @param attributeName
     * @return
     */
    public Class<?>[] getClassArray(String attributeName) {
        return doGet(attributeName, Class[].class);
    }

    /**
     * 通过属性名 获取一个属性值
     * @param attributeName
     * @param expectedType
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T doGet(String attributeName, Class<T> expectedType) {

        Object value = this.get(attributeName);
        Assert.notNull(value, format("Attribute '%s' not found", attributeName));
        return (T) value;
    }
}
