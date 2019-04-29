package spring.road.beans.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;
import spring.road.aop.ReflectiveMethodInvocation;
import spring.road.aop.aspectj.AspectJAfterReturningAdvise;
import spring.road.aop.aspectj.AspectJAfterThrowingAdvise;
import spring.road.aop.aspectj.AspectJBeforeAdvise;
import spring.road.aop.aspectj.AspectJExpressionPointcut;
import spring.road.aop.framwork.AopConfig;
import spring.road.aop.framwork.AopSupport;
import spring.road.aop.framwork.CglibProxyFactory;
import spring.road.beans.models.Person;
import spring.road.beans.models.UserDao;
import spring.road.beans.models.aop.TransactionManager;
import spring.road.beans.models.scan.GameService;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public class AspectJAdvisetTest {
    private static AspectJBeforeAdvise beforeAdvice = null;
    private static AspectJAfterReturningAdvise afterAdvice = null;
    private static AspectJAfterThrowingAdvise throwingAdvise = null;
    private static AspectJExpressionPointcut pc = null;
    private TransactionManager tx;
    List<MethodInterceptor> methodInterceptors= new ArrayList<MethodInterceptor>();
    Person person;
    TransactionManager transactionManager=new TransactionManager();

    @Before
    public void init() throws NoSuchMethodException {
        tx = new TransactionManager();
        String expression = "execution(* spring.road.beans.models.scan.*.playGames(..))";
        pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);
//        beforeAdvice = new AspectJBeforeAdvise(TransactionManager.class.getMethod("start"),
//                pc, transactionManager);
//        afterAdvice = new AspectJAfterReturningAdvise(
//                TransactionManager.class.getMethod("commit"),
//                pc, transactionManager);
//        throwingAdvise = new AspectJAfterThrowingAdvise(TransactionManager.class.getMethod("rollback"),
//                pc, transactionManager);
        List<MethodInterceptor> methodInterceptors = new ArrayList<MethodInterceptor>();
        methodInterceptors.add(beforeAdvice);
        methodInterceptors.add(afterAdvice);
        person = new Person("lijinpeng", 26, false, new UserDao());
    }

    @Test
    //验证方法执行器
    public void methodInvocationTest() throws Throwable {
        GameService gameService = new GameService();

        gameService.setPerson(person);
        ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(gameService, GameService.class.getMethod("playGames"), null, methodInterceptors);
        invocation.proceed();
    }

    @Test
    //验证代理
    public void testGetProxy() throws Exception {
        AopConfig aopConfig = new AopSupport();
//        aopConfig.addAdvice(beforeAdvice);
//        aopConfig.addAdvice(afterAdvice);
//        aopConfig.addAdvice(throwingAdvise);
        GameService gameService = new GameService();
        gameService.setPerson(person);
        aopConfig.setTargetObject(gameService);
        CglibProxyFactory proxyFactory = new CglibProxyFactory(aopConfig);
        GameService gameServiceProxy = (GameService) proxyFactory.getProxy();
        gameServiceProxy.playGames();
    }


}
