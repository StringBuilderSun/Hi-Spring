package spring.road.beans.setter;

import javafx.beans.binding.ObjectExpression;
import org.junit.Assert;
import org.junit.Test;
import spring.road.beans.models.Person;
import spring.road.context.support.ClassPathXmlApplicationContext;

/**
 * 构造器 注入测试
 * User: StringBuilderSun
 * Created by Shanghai on 2019/4/5.
 */
public class ConstructorSetTest {
    @Test
    public void constructorsetTest() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        Person person = (Person) applicationContext.getBean("person");
        Assert.assertNotNull(person);
        Assert.assertEquals("dangwendi", person.getName());
        Assert.assertEquals(26, person.getAge());
        Assert.assertNotNull(person.getUserDao());
        Assert.assertEquals(true, person.isSex());
    }
}
