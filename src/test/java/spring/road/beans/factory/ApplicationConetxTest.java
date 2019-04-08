package spring.road.beans.factory;

import org.junit.Assert;
import org.junit.Test;
import spring.road.context.support.ClassPathXmlApplicationContext;
import spring.road.context.support.FileSystemXmlApplicationContext;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public class ApplicationConetxTest {
    @Test
    public void classPathXmlAppllicationTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        Object bean = context.getBean("beanService");
        Assert.assertEquals("BeanService",bean.getClass().getSimpleName());
    }
//    @Test
//    public void fileSystemXmlApplicationContext()
//    {
//        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("D:\\ProjectWorkSpace\\Hi-Spring\\src\\main\\resources\\spring\\spring-context.xml");
//        Object bean = context.getBean("beanService");
//        Assert.assertEquals("BeanService",bean.getClass().getSimpleName());
//    }
}
