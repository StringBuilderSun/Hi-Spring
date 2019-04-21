package spring.road.beans.models.aop;

import lombok.extern.slf4j.Slf4j;
import spring.road.beans.utils.MessageTracerUtils;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/19.
 */
@Slf4j
public class TransactionManager {
    public void start() {
        log.info("start tx");
        MessageTracerUtils.addMessage("start tx");
    }

    public void commit() {
        log.info("commit tx");
        MessageTracerUtils.addMessage("commit tx");
    }

    public void rollback() {
        log.info("rollback tx");
        MessageTracerUtils.addMessage("rollback tx");
    }
}
