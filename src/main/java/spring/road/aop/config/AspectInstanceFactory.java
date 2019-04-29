package spring.road.aop.config;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import spring.road.beans.factory.BeanFactory;
import spring.road.beans.factory.BeanFactoryAware;

/**
 * 切面类获取实例工厂
 * 通过该工厂可以获取一个切面对象
 * User: lijinpeng
 * Created by Shanghai on 2019/4/22.
 */
public class AspectInstanceFactory implements BeanFactoryAware {
    /**
     * 切面类的 beanId
     */
    @Setter
    private String aspectBeanName;

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        if (StringUtils.isEmpty(aspectBeanName)) {
            throw new IllegalArgumentException("'aspectBeanName' is required");
        }
    }
    public Object getAspectInstance() {
        return this.beanFactory.getBean(aspectBeanName);
    }
}
