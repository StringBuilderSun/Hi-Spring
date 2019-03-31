package spring.road.beans.exception;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable e) {
        super(msg, e);
    }
}
