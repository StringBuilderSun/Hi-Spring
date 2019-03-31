package spring.road.beans;

import org.junit.Assert;
import org.junit.Test;
import spring.road.context.support.ClassPathXmlApplicationContext;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public class SingleBeanFactoryTest {

    @Test
    public void singleBeanTest() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        Object beanService1 = applicationContext.getBean("beanService");
        Object beanService2 = applicationContext.getBean("beanService");
        Assert.assertEquals(beanService1, beanService2);
    }

}
