package spring.road.beans.aop;

import org.junit.Assert;
import org.junit.Test;
import spring.road.aop.aspectj.AspectJExpressionPointcut;
import spring.road.aop.config.AspectInstanceFactory;
import spring.road.aop.config.MethodLocatingFactory;
import spring.road.beans.config.ConstructorArgument;
import spring.road.beans.config.RuntimeBeanNameReference;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.support.DefaultBeanFactory;
import spring.road.context.factory.XmlBeanDefaultReader;
import spring.road.context.io.ClassPathResource;
import spring.road.context.io.Resource;

import java.util.List;

/**
 * 验证ICO将切面方法加载成一个BeanDefinition对象
 * 比如aop:before 封装成一个IOC容器对象的结构BeanDefinition
 * 该BeanDefinition包含三个参数
 * 参数1:MethodLocatingFactory
 * 参数2:PointCut
 * 参数3:AspectInstanceFactory
 * Created by lijinpeng on 2019/4/26.
 */
public class AdviseBeandefinationTest {
    @Test
    public void adviseBeandefinationTest() {
        Resource resource = new ClassPathResource("spring/spring-context.xml");
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefaultReader reader = new XmlBeanDefaultReader(beanFactory);
        reader.loadBeanDifinitions(resource);
        BeanDefinition beforeAdviceBd = beanFactory.getBeanDefinition("spring.road.aop.aspectj.AspectJBeforeAdvise#0");
//        beanFactory.getBeanDefinition("spring.road.aop.aspectj.AspectJAfterThrowingAdvise#0");
//        beanFactory.getBeanDefinition("spring.road.aop.aspectj.AspectJAfterReturningAdvise#0");
        //三个参数值
        Assert.assertEquals(3, beforeAdviceBd.getConstructorArgument().getArgumentCount());
        List<ConstructorArgument.ValueHolder> argumentValues = beforeAdviceBd.getConstructorArgument().getArgumentValues();
        //第一个参数 是  MethodLocatingFactory 的 封装成一个IOC容器对象的结构BeanDefinition
        BeanDefinition methodLocatingFactory = (BeanDefinition) argumentValues.get(0).getValue();
        Assert.assertEquals(methodLocatingFactory.getBeanClassName(), MethodLocatingFactory.class.getName());
        //第二个参数 pointcut-ref="games" 是个引用类型数据
        RuntimeBeanNameReference pointCut = (RuntimeBeanNameReference) argumentValues.get(1).getValue();
        Assert.assertEquals("games", pointCut.getBeanName());
        //也会声明一个 BeanDefinition
        BeanDefinition games = beanFactory.getBeanDefinition("games");
        Assert.assertEquals(AspectJExpressionPointcut.class.getName(), games.getBeanClassName());
        //第三额参数是 AspectInstanceFactory 的 BeanDefinition
        BeanDefinition aspectInstanceFactory = (BeanDefinition) argumentValues.get(2).getValue();
        Assert.assertEquals(aspectInstanceFactory.getBeanClassName(), AspectInstanceFactory.class.getName());
    }
}
