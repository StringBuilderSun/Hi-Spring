package spring.road.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配器
 * 校验是否匹配方法
 * User: lijinpeng
 * Created by Shanghai on 2019/4/17.
 */
public interface MethodMatcher {
    /**
     * 传入一个方法 校验是否匹配当前方法
     * @param method
     * @return
     */
    boolean matches(Method method/*, Class<?> targetClass*/);
}
