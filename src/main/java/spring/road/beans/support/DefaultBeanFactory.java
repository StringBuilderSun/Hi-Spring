package spring.road.beans.support;

import spring.road.beans.config.ConfigurableBeanFactory;
import spring.road.beans.core.BeanFactory;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;
import spring.road.beans.exception.BeanCreateException;
import spring.road.beans.utils.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2019/3/29.
 */
public class DefaultBeanFactory implements BeanDefinitionRegistry, ConfigurableBeanFactory, BeanFactory {
    /**
     * beans 声明集合
     */
    private Map<String, BeanDefinition> beanDefinitionsMap = new ConcurrentHashMap<String, BeanDefinition>();
    /**
     * 类加载器
     */
    private ClassLoader classLoader;

    public BeanDefinition getBeanDefinition(String beanId) {
        return beanDefinitionsMap.get(beanId);
    }

    public void beanDefinationRegister(String beanName, BeanDefinition definition) {
        beanDefinitionsMap.put(beanName, definition);
    }

    public Object getBean(String beanId) {
        //首先得到bean声明
        BeanDefinition beanDefinition = getBeanDefinition(beanId);
        if (beanDefinition == null) {
            return null;
        }

        String beanName = beanDefinition.getClassName();
        try {
            Class<?> objectBean = getClassLoader().loadClass(beanName);
            return objectBean.newInstance();
        } catch (Exception e) {
            throw new BeanCreateException(beanName, e.getMessage(), e);
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }
}
