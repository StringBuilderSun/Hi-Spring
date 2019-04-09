package spring.road.beans.definition;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public interface BeanDefinitionRegistry  {
    /**
     * 获取bean声明
     * @param beanName
     * @return
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 注册bean
     * @param beanName
     * @param definition
     */
    void beanDefinationRegister(String beanName,BeanDefinition definition);
}
