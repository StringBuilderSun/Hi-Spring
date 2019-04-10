package spring.road.context.factory;

import spring.road.beans.config.ConfigurableBeanFactory;
import spring.road.beans.postProcessor.AutowiredAnnotationProcessor;
import spring.road.beans.support.DefaultBeanFactory;
import spring.road.beans.utils.ClassUtils;
import spring.road.context.core.ApplicationContext;
import spring.road.context.io.Resource;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory beanFactory;
    private ClassLoader classLoader;

    public AbstractApplicationContext(String configPath) {
        beanFactory = new DefaultBeanFactory();
        XmlBeanDefaultReader reader = new XmlBeanDefaultReader(beanFactory);
        Resource resource = this.getResource(configPath);
        reader.loadBeanDifinitions(resource);
        registerBeanPostProcessors(beanFactory);
    }

    /**
     * 通过beanId 获取bean
     *
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) {
        return beanFactory.getBean(beanId);
    }

    /**
     * 由子类决定根据文件名 如何加载
     *
     * @param configPath
     * @return
     */
    public abstract Resource getResource(String configPath);

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    /**
     * 注册PostProcessor处理器  genBean()时候执行
     *
     * @param beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {
        AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);

    }
}
