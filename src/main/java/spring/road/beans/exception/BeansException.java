package spring.road.beans.exception;

/**
 * Bean 异常基类  继承与检查时异常
 * Created by Administrator on 2019/3/30.
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable e) {
        super(msg, e);
    }
}
