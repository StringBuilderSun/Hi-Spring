package spring.road.beans.factory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.exception.BeanDefinitionException;
import spring.road.beans.models.BeanService;
import spring.road.beans.support.DefaultBeanFactory;
import spring.road.context.factory.XmlBeanDefaultReader;
import spring.road.context.io.ClassPathResource;

/**
 * Created by Administrator on 2019/3/29.
 */
public class BeanFactoryTest {
    private DefaultBeanFactory factory = null;
    private XmlBeanDefaultReader reader = null;

    @Before
    public void beforeTest() {

        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefaultReader(factory);
    }

    @Test
    public void beanFacturyTest() {
        reader.loadBeanDifinitions(new ClassPathResource("spring/spring-context.xml"));
        BeanDefinition beanDefinition = factory.getBeanDefinition("beanService");
        Assert.assertEquals("spring.road.beans.models.BeanService", beanDefinition.getBeanClassName());
        BeanService beanService = (BeanService) factory.getBean("beanService");
        Assert.assertNotNull(beanService);

    }

    @Test
    public void exceptionTest() {
        try {
            reader.loadBeanDifinitions(new ClassPathResource("spring/spring-xxxxxx.xml"));
        } catch (BeanDefinitionException ex) {
            return;
        }
        Assert.fail("expect BeanDefinitionException");
    }
}
