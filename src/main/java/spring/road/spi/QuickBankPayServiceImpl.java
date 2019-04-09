package spring.road.spi;

import lombok.extern.slf4j.Slf4j;

/**
 * 快捷服务实现类
 * Created by lijinpeng on 2019/4/9.
 */

public class QuickBankPayServiceImpl implements BankPayService {
    public String pay(int amt) {
        return "调用了快捷支付，支付金额:"+amt;
    }
}
