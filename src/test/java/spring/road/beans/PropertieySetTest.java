package spring.road.beans;

import org.junit.Assert;
import org.junit.Test;
import spring.road.beans.models.BeanService;
import spring.road.context.support.ClassPathXmlApplicationContext;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public class PropertieySetTest {

    @Test
    public void propertySetTest() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        BeanService beanService = (BeanService) applicationContext.getBean("beanService");
        Assert.assertNotNull(beanService.getMapper());
        Assert.assertEquals("lijinpeng", beanService.getName());
    }
}
