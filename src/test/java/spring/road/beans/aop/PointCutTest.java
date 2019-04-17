package spring.road.beans.aop;

import org.junit.Assert;
import org.junit.Test;
import spring.road.aop.MethodMatcher;
import spring.road.aop.aspectj.AspectJExpressionPointcut;
import spring.road.beans.models.BeanService;
import spring.road.beans.models.scan.GameService;

import java.lang.reflect.Method;

/**
 * 切入点测试类
 * User: lijinpeng
 * Created by Shanghai on 2019/4/17.
 */
public class PointCutTest {
    @Test
    public void pointCutTest() throws NoSuchMethodException {

        String expression = "execution(* spring.road.beans.models.scan.*.playGames(..))";
        AspectJExpressionPointcut aspectJEex = new AspectJExpressionPointcut();
        aspectJEex.setExpression(expression);
        MethodMatcher matcher = aspectJEex.getMethodMatcher();
        Method playGames = GameService.class.getDeclaredMethod("playGames");
        Method getPerson = GameService.class.getDeclaredMethod("getPerson");
        Method bean_playGames = BeanService.class.getDeclaredMethod("playGames");
        Assert.assertTrue(matcher.matches(playGames));
        //无法匹配相同包下不同的方法
        Assert.assertFalse(matcher.matches(getPerson));
        //无法匹配非限定包下的同名方法
        Assert.assertFalse(matcher.matches(bean_playGames));


    }
}
