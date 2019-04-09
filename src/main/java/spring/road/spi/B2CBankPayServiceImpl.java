package spring.road.spi;

/**
 * 网关B2C支付
 * Created by lijinpeng on 2019/4/9.
 */
public class B2CBankPayServiceImpl implements BankPayService {
    public String pay(int amt) {
        return "调用了网关B2C支付，支付金额:"+amt;
    }
}
