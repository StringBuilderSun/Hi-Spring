package spring.road.aop;

/**
 * 通过表达式 校验一个方法是否是一个切入点方法
 * 简单点理解就是 校验一个方法是否应该被拦截或增强
 * User: lijinpeng
 * Created by Shanghai on 2019/4/17.
 */
public interface Pointcut {
    /**
     * 获取一个方法匹配器
     * 该匹配器支持传入一个方法 校验是否能被匹配
     *
     * @return
     */
    MethodMatcher getMethodMatcher();

    /**
     * 获取切入点表达式
     *
     * @return
     */
    String getExpression();
}
