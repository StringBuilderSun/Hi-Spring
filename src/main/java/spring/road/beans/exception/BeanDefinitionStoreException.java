package spring.road.beans.exception;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/22.
 */
public class BeanDefinitionStoreException extends BeansException {
    public BeanDefinitionStoreException(String msg) {
        super(msg);
    }

    public BeanDefinitionStoreException(String msg, Throwable e) {
        super(msg, e);
    }
}
