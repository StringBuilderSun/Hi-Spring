package spring.road.beans.factory;

/**
 * 通过该接口可以设置一个bean工厂
 * User: lijinpeng
 * Created by Shanghai on 2019/4/22.
 */
public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory);
}
