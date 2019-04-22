package spring.road.aop.config;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import spring.road.beans.config.PropertyValue;
import spring.road.beans.config.RuntimeBeanNameReference;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;
import spring.road.beans.definition.GenericBeanDefinition;
import spring.road.beans.support.BeanDefinitionReaderUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * AOP配置解析类
 * 专门用于解析AOP标签下的切面属性配置
 * User: lijinpeng
 * Created by Shanghai on 2019/4/22.
 */
public class ConfigBeanDefinitionParser {
    private static final String ASPECT = "aspect";
    private static final String EXPRESSION = "expression";
    private static final String ID = "id";
    private static final String POINTCUT = "pointcut";
    private static final String POINTCUT_REF = "pointcut-ref";
    private static final String REF = "ref";
    private static final String BEFORE = "before";
    private static final String AFTER = "after";
    private static final String AFTER_RETURNING_ELEMENT = "after-returning";
    private static final String AFTER_THROWING_ELEMENT = "after-throwing";
    private static final String AROUND = "around";
    private static final String ASPECT_NAME_PROPERTY = "aspectName";

    /**
     * 解析 AOP config 将每一个 aop 标签生成一个beanDefinition
     *
     * @param element  AOP config
     * @param registry 需要将生成的aop beanDefinition注册到IOC中
     */
    public void parse(Element element, BeanDefinitionRegistry registry) {
        List<Element> elements = element.elements();
        for (Element childEle : elements) {
            if (this.ASPECT.equals(childEle.getName())) {
                //如果是一个切面类配置 根据配置 生成一个BeanDefinition
                aspectParse(childEle, registry);
            }
        }
    }

    /**
     * 核心方法-解析aop 切面配置类
     * 将每个aop:before aop:after-returning封装成一个个 BeanDefinitation
     * BeanDefinitation又包含三个 参数
     * 参数1：方法定位器MethodLocatingFactory  通过该方法可以找到要执行的切面方法并执行
     * 参数2 : PointCut  AspectJExpressionPointcut  通过该类可以获取一个方法匹配器，使用express表达式 定位到需要拦截的方法
     * 参数3:AspectInstanceFactory 该类可以获取一个切面类 并交给 MethodLocatingFactory 执行切面方法
     * 注意：这三个参数又都是 BeanDefinitation
     *
     * @param aspectElement
     * @param registry
     */
    private void aspectParse(Element aspectElement, BeanDefinitionRegistry registry) {
        String id = aspectElement.attributeValue(ID);
        String aspectName = aspectElement.attributeValue(REF);
        //获取配置的aop:before   aop:after-returning 等切面类
        List<Element> aopAspectElements = aspectElement.elements();
        //用以存放生成的aop beandefinition
        List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
        List<RuntimeBeanNameReference> beanReferences = new ArrayList<RuntimeBeanNameReference>();
        boolean adviceFoundAlready = false;
        for (int i = 0; i < aopAspectElements.size(); i++) {
            Element element = aopAspectElements.get(i);
            if (isAdviceNode(element)) {
                //1、如果是aop:before   aop:after-returning 这样的切面方法 生成 beandefinition
                if (!adviceFoundAlready) {
                    //未被加载过 才会加载
                    adviceFoundAlready = true;
                    if (StringUtils.isEmpty(aspectName)) {
                        return;
                    }
                    beanReferences.add(new RuntimeBeanNameReference(aspectName));
                }
                //advisorDefinition 就是为 aop:before   aop:after-returning  生成的advisorDefinition
                GenericBeanDefinition advisorDefinition = parseAdvice(
                        aspectName, i, aspectElement, element, registry, beanDefinitions, beanReferences);
                beanDefinitions.add(advisorDefinition);
            }
        }
    }

    /**
     * 生成 advisorDefinition 并自动设置三个构造参数
     *
     * @param aspectName      切面方法 before  after-returning 等 用来生成切面类
     * @param order           切面类编号
     * @param aspectElement   切面类元素
     * @param element         aop config
     * @param registry        ICO注册
     * @param beanDefinitions bean声明
     * @param beanReferences
     * @return
     */
    private GenericBeanDefinition parseAdvice(String aspectName, int order, Element aspectElement, Element element, BeanDefinitionRegistry registry, List<BeanDefinition> beanDefinitions, List<RuntimeBeanNameReference> beanReferences) {
        GenericBeanDefinition methodDefinition = new GenericBeanDefinition(MethodLocatingFactory.class);
        methodDefinition.getpropertyValueList().add(new PropertyValue("targetBeanName", aspectName));
        methodDefinition.getpropertyValueList().add(new PropertyValue("methodName", aspectElement.attributeValue("method")));
        methodDefinition.setSynthetic(true);

        // create instance factory definition
        GenericBeanDefinition aspectFactoryDef = new GenericBeanDefinition(AspectInstanceFactory.class);
        aspectFactoryDef.getpropertyValueList().add(new PropertyValue("aspectBeanName", aspectName));
        aspectFactoryDef.setSynthetic(true);
        // register the pointcut
        GenericBeanDefinition adviceDef = createAdviceDefinition(aspectElement, registry, aspectName, order, methodDefinition, aspectFactoryDef,
                beanDefinitions, beanReferences);
        adviceDef.setSynthetic(true);

        // register the final advisor
        BeanDefinitionReaderUtils.registerWithGeneratedName(adviceDef, registry);
        return adviceDef;
    }

    private GenericBeanDefinition createAdviceDefinition(Element aspectElement, BeanDefinitionRegistry registry, String aspectName, int order, GenericBeanDefinition methodDefinition, GenericBeanDefinition aspectFactoryDef, List<BeanDefinition> beanDefinitions, List<RuntimeBeanNameReference> beanReferences) {
        return null;
    }

    private boolean isAdviceNode(Element element) {
        String name = element.getName();
        return (BEFORE.equals(name) || AFTER.equals(name) || AFTER_RETURNING_ELEMENT.equals(name) ||
                AFTER_THROWING_ELEMENT.equals(name) || AROUND.equals(name));
    }

}
