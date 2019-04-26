package spring.road.beans.factory;

/**
 * 扩展获取bean实例的方式
 * 允许通过其他方式获取bean实例 比如 通过代理
 * Created by lijinpeng on 2019/4/26.
 */
public interface FactoryBean<T> {
    /**
     * 获取bean实例
     *
     * @return
     */
    T getObject();

    /**
     * 获取bean 的 class类型
     *
     * @return
     */
    Class<?> getObjectType();
}
