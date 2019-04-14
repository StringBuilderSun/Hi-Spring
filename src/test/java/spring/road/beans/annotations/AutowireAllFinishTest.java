package spring.road.beans.annotations;

import org.junit.Assert;
import org.junit.Test;
import spring.road.beans.models.BeanService;
import spring.road.beans.models.Person;
import spring.road.beans.models.scan.GameService;
import spring.road.beans.models.scan.impl.Boss;
import spring.road.context.support.ClassPathXmlApplicationContext;

/**
 * Autowire注解完成全流程验证
 * User: lijinpeng
 * Created by Shanghai on 2019/4/10.
 */
public class AutowireAllFinishTest {
    @Test
    public void autowireAllFinishTest() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        GameService gameService = (GameService) applicationContext.getBean("gameService");
        Assert.assertEquals("dangwendi", gameService.getPerson().getName());
        BeanService beanService=gameService.getBeanService();
        Assert.assertNotNull(beanService);
        Assert.assertTrue(!beanService.isSex());
        Assert.assertEquals("lijinpeng",beanService.getName());
    }
}
