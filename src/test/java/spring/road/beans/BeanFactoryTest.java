package spring.road.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spring.road.beans.core.BeanFactory;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.exception.BeanDefinitionException;
import spring.road.beans.support.DefaultBeanFactory;

/**
 * Created by Administrator on 2019/3/29.
 */
public class BeanFactoryTest {
    private BeanFactory factory = null;

    @Before
    public void beforeTest() {
        factory = new DefaultBeanFactory("/spring/spring-context.xml");
    }

    @Test
    public void beanFacturyTest() {

        BeanDefinition beanDefinition = factory.getBeanDefinition("beanService");
        Assert.assertEquals("spring.road.beans.BeanService", beanDefinition.getClassName());
        BeanService beanService = (BeanService) factory.getBean("beanService");
        Assert.assertNotNull(beanService);

    }

    @Test
    public void exceptionTest() {
        try {
            factory = new DefaultBeanFactory("spring-context.xml");
        } catch (BeanDefinitionException ex) {
            return;
        }
        Assert.fail("expect BeanDefinitionException");
    }
}
