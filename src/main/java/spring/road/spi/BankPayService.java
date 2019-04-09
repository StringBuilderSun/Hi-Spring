package spring.road.spi;

/**
 * 付款服务
 * 使用java spi机制 根据策略从本地配置中查找实现类
 * Created by lijinpeng on 2019/4/9.
 */
public interface BankPayService {
    String pay(int amt);
}
