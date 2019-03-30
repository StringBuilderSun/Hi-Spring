package spring.road.beans.exception;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2019/3/30.
 */
@Getter
public class BeanCreateException extends BeansException {
    private String beanName;

    public BeanCreateException(String msg) {
        super(msg);
    }

    public BeanCreateException(String msg, Throwable e) {
        super(msg, e);
    }

    public BeanCreateException(String beanName, String msg) {
        super(StringUtils.join(new Object[]{"error create bean:", beanName, " errorMsg:", msg}));
        this.beanName = beanName;
    }

    public BeanCreateException(String beanName, String msg, Throwable e) {
        this(beanName, msg);
        initCause(e);
    }

}
