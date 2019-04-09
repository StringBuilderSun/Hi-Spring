package spring.road.beans.spi;

import org.junit.Test;
import spring.road.spi.BankPayService;

import java.util.ServiceLoader;

/**
 * Created by lijinpeng on 2019/4/9.
 */
public class SpiServiceTest {
    @Test
    public void spiTest() {
        //可能会有多个实现
        ServiceLoader<BankPayService> payServices = ServiceLoader.load(BankPayService.class);
        for(BankPayService payService:payServices)
        {
            System.out.println(payService.pay(100));
        }
    }
}
