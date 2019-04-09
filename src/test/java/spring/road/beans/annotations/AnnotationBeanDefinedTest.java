package spring.road.beans.annotations;

import org.junit.Assert;
import org.junit.Test;
import spring.road.beans.support.DefaultBeanFactory;
import spring.road.context.annotations.AnnotatedBeanDefinition;
import spring.road.context.annotations.ScannedGenericBeanDefinition;
import spring.road.context.factory.XmlBeanDefaultReader;
import spring.road.context.io.ClassPathResource;
import spring.road.context.io.Resource;
import spring.road.stereotype.Component;

/**
 * 扫描bean声明 测试
 * Created by lijinpeng on 2019/4/9.
 */
public class AnnotationBeanDefinedTest {
    @Test
    public void annotationBeanDefinedTest() {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefaultReader reader = new XmlBeanDefaultReader(factory);
        Resource resource = new ClassPathResource("spring/spring-context.xml");
        reader.loadBeanDifinitions(resource);
        String annotation = Component.class.getName();
        Assert.assertEquals("spring.road.beans.models.scan.GameService", factory.getBeanDefinition("gameService").getBeanClassName());
        Assert.assertEquals("spring.road.beans.models.scan.GameService", factory.getBeanDefinition("gameService").getBeanClassName());
        Assert.assertEquals("spring.road.beans.models.scan.impl.Boss", factory.getBeanDefinition("boss").getBeanClassName());
        Assert.assertTrue((factory.getBeanDefinition("roles") instanceof ScannedGenericBeanDefinition));
        Assert.assertTrue((factory.getBeanDefinition("gameService") instanceof ScannedGenericBeanDefinition));
        Assert.assertTrue((factory.getBeanDefinition("boss") instanceof ScannedGenericBeanDefinition));
        AnnotatedBeanDefinition definition = (AnnotatedBeanDefinition) factory.getBeanDefinition("boss");
        Assert.assertTrue(definition.getAnnotationMetadata().hasAnnotation(annotation));

    }
}
