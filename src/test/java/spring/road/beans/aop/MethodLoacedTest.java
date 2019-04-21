package spring.road.beans.aop;

import org.junit.Assert;
import org.junit.Test;
import spring.road.beans.utils.MessageTracerUtils;
import spring.road.aop.config.MethodLocatingFactory;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.support.DefaultBeanFactory;
import spring.road.context.factory.XmlBeanDefaultReader;
import spring.road.context.io.ClassPathResource;
import spring.road.context.io.Resource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 方法定位测试类
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public class MethodLoacedTest {
    @Test
    public void findMethodTest() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefaultReader reader = new XmlBeanDefaultReader(beanFactory);
        Resource resource = new ClassPathResource("spring/spring-context.xml");
        reader.loadBeanDifinitions(resource);
        MethodLocatingFactory methodFactory = new MethodLocatingFactory();
        methodFactory.setTargetBeanName("tx");
        methodFactory.setMethodName("start");
        methodFactory.setBeanFactory(beanFactory);
        Method method = (Method) methodFactory.getObject();
        Assert.assertEquals("start", method.getName());
        BeanDefinition bd = beanFactory.getBeanDefinition("tx");
        method.invoke(bd.getBeanClass().newInstance());
        //断言 获取的方法 是目标方法
        Assert.assertEquals(1, MessageTracerUtils.getMessages().size());
        Assert.assertEquals("start tx", MessageTracerUtils.getMessages().get(0));
    }
}
