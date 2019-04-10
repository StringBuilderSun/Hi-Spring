package spring.road.beans.models.scan.impl;

import spring.road.beans.annation.Autowired;
import spring.road.beans.models.BeanService;
import spring.road.stereotype.Component;

/**
 * Created by lijinpeng on 2019/4/9.
 */
@Component(value = "boss")
public class Boss {
    @Autowired
    private BeanService beanService;
    public BeanService getBeanService()
    {
        return beanService;
    }
}
