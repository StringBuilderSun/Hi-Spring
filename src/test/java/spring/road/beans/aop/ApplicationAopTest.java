package spring.road.beans.aop;

import org.junit.Test;
import spring.road.beans.models.scan.GameService;
import spring.road.context.support.ClassPathXmlApplicationContext;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/23.
 */
public class ApplicationAopTest {
    @Test
    public void aopTest() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-context.xml");
        GameService gameService = (GameService) applicationContext.getBean("gameService");
        gameService.playGames();

    }
}
