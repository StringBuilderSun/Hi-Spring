package spring.road.beans.annation;

import spring.road.beans.factory.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * 字段解析元素
 * 通过这个类可以将方法 字段 从IOC中查找中对应的属性 完成注入功能
 * Created by lijinpeng on 2019/4/10.
 */
public abstract class InjectionElement {
    /**
     * 可接收method  Filed
     */
    public Member member;
    /**
     * 从bean工厂获取符合member类型的bean 对meber赋值
     */
    public AutowireCapableBeanFactory factory;

    public InjectionElement(Member member, AutowireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    /**
     * 对目标对象完成属性注入
     * @param target
     */
    public abstract void inject(Object target);
}
