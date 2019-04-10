package spring.road.beans.annation;

import java.lang.annotation.Target;
import java.util.List;

/**
 * 获取一个类中所有允许属性自动注入的集合 完成注入功能
 * User: lijinpeng
 * Created by Shanghai on 2019/4/10.
 */
public class InjectionMetadata {
    /**
     * 要注入属性值的目标类
     */
    private Class<?> target;
    /**
     * 目标类需要注入的属性集合 由其自身完成注册功能
     */
    private List<InjectionElement> injectionElements;

    public InjectionMetadata(Class<?> target, List<InjectionElement> injectionElements) {
        this.target = target;
        this.injectionElements = injectionElements;
    }

    public List<InjectionElement> getInjectionElements() {
        return this.injectionElements;
    }

    public void inject() {
        if (injectionElements != null && injectionElements.size() > 0) {
            for (InjectionElement element : injectionElements) {
                element.inject(target);
            }
        }
    }
}
