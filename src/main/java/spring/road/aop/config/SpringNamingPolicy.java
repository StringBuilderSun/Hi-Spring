package spring.road.aop.config;

import net.sf.cglib.core.DefaultNamingPolicy;

/**
 * 代理命名规范
 * User: lijinpeng
 * Created by Shanghai on 2019/4/21.
 */
public class SpringNamingPolicy extends DefaultNamingPolicy {

    public static SpringNamingPolicy INSTANCE=new SpringNamingPolicy();
    public SpringNamingPolicy() {
    }

    @Override
    protected String getTag() {
        return "BySpringCJLIB";
    }
}
