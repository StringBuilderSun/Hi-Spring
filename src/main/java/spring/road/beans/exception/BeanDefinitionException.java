package spring.road.beans.exception;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public class BeanDefinitionException extends BeansException {

    public BeanDefinitionException(String msg) {
        super(msg);
    }

    public BeanDefinitionException(String msg, Throwable e) {
        super(msg, e);
    }

}
