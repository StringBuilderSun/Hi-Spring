package spring.road.beans.postProcessor;

import spring.road.beans.annation.Autowired;
import spring.road.beans.annation.InjectionElement;
import spring.road.beans.annation.InjectionMetadata;
import spring.road.beans.exception.BeanCreateException;
import spring.road.beans.exception.BeansException;
import spring.road.beans.factory.AutowireCapableBeanFactory;
import spring.road.beans.factory.AutowiredFieldElement;
import spring.road.beans.utils.ReflectionUtils;
import spring.road.core.annotations.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 获取类的属性是否使用了该类声明的注解  如果使用了 放入的合适的集合中 等待使用
 * User: lijinpeng
 * Created by Shanghai on 2019/4/10.
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {


    private AutowireCapableBeanFactory beanFactory;
    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<Class<? extends Annotation>>();
    /**
     * 默认请求参数值设置为true
     */
    private Boolean requiredParameterValue = true;
    /**
     * 获取Autowired设置的属性值
     */
    private java.lang.String requiredParameterName = "required";

    public AutowiredAnnotationProcessor() {
        this.autowiredAnnotationTypes.add(Autowired.class);
    }

    /**
     * 不使用构造器注入 因为方便后续 可以由其他项目单独实现beanfactory
     *
     * @param beanFactory
     */
    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        //nothing to do
        return null;
    }

    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        //nothing to do
        return true;
    }

    /**
     * 主要在这里完成属性的自动注入
     *
     * @param bean
     * @param beanName
     * @throws BeansException
     */
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        Class<?> target = bean.getClass();
        InjectionMetadata metadata = buildAutowiringMetadata(target);
        try {
            metadata.inject(bean);
        } catch (Throwable ex) {
            throw new BeanCreateException(beanName, "Injection of autowired dependencies failed", ex);
        }
    }

    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        //nothing to do
        return null;
    }

    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        //nothing to do
        return bean;
    }

    /**
     * 构建类的属性自动注入的数据集合
     *
     * @param target
     * @return
     */
    private InjectionMetadata buildAutowiringMetadata(Class<?> target) {
        Class<?> clazz = target;
        LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();
        //循环查找父类 属性是否使用了注解  依次添加到集合
        do {
            LinkedList<InjectionElement> currEles = new LinkedList<InjectionElement>();
            for (Field field : clazz.getDeclaredFields()) {
                //存在该注解
                Annotation an = findAutowiredAnnotation(field);
                if (an != null) {
                    boolean required = determineRequiredStatus(an);
                    //检查是静态属性 不能赋值
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    currEles.add(new AutowiredFieldElement(field, required, beanFactory));
                }
            }
            //下面还可以实现方法注入 与属性注入一样
            for (Method method : clazz.getDeclaredMethods()) {
                //TODO 处理方法注入
            }
            elements.addAll(0, currEles);
            clazz = clazz.getSuperclass();
        } while (clazz != null && clazz != Object.class);
        return new InjectionMetadata(elements);
    }

    /**
     * 查找是否有该注解
     *
     * @param ao
     * @return
     */
    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) {
                return ann;
            }
        }
        return null;
    }

    protected boolean determineRequiredStatus(Annotation ann) {
        try {
            Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
            if (method == null) {
                // 如果像 @Inject and @Value 没有一个属性 "required"
                // -> 设置默认状态
                return true;
            }
            return (this.requiredParameterValue == ReflectionUtils.invokeMethod(method, ann));
        } catch (Exception ex) {
            return true;
        }
    }
}
