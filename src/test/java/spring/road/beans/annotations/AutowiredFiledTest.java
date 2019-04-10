package spring.road.beans.annotations;

import org.junit.Assert;
import org.junit.Test;
import spring.road.beans.factory.AutowireCapableBeanFactory;
import spring.road.beans.factory.AutowiredFieldElement;
import spring.road.beans.models.scan.GameService;
import spring.road.beans.support.DefaultBeanFactory;
import spring.road.context.factory.XmlBeanDefaultReader;
import spring.road.context.io.ClassPathResource;
import spring.road.context.io.Resource;
import spring.road.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Field;

/**
 * 验证字段的自动赋值
 * Created by lijinpeng on 2019/4/10.
 */
public class AutowiredFiledTest {
    @Test
    public void autowiredTest() throws NoSuchFieldException {
        Resource resource = new ClassPathResource("spring/spring-context.xml");
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefaultReader reader = new XmlBeanDefaultReader(beanFactory);
        reader.loadBeanDifinitions(resource);
        GameService service = new GameService();
        Field field = GameService.class.getDeclaredField("person");
        AutowiredFieldElement fieldElement = new AutowiredFieldElement(field, true, beanFactory);
        fieldElement.inject(service);
        Assert.assertNotNull(service.getPerson());
        Assert.assertEquals("dangwendi", service.getPerson().getName());
    }
}
