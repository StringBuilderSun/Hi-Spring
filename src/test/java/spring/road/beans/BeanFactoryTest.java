package spring.road.beans;

import org.junit.Assert;
import org.junit.Test;
import spring.road.beans.core.BeanFactory;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.support.DefaultBeanFactory;

/**
 * Created by Administrator on 2019/3/29.
 */
public class BeanFactoryTest {
    @Test
    public void beanFacturyTest() {
       String file= Thread.currentThread().getContextClassLoader().getResource("spring/spring-context.xml").getPath();
        BeanFactory factory = new DefaultBeanFactory(file);
        BeanDefinition beanDefinition = factory.getBeanDefinition("beanService");
        Assert.assertEquals("spring.road.beans.BeanService", beanDefinition.getClassName());
        BeanService beanService = (BeanService) factory.getBean("beanService");
        Assert.assertNotNull(beanService);

    }
}
