package spring.road.aop.config;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import spring.road.aop.aspectj.AspectJAfterReturningAdvise;
import spring.road.aop.aspectj.AspectJAfterThrowingAdvise;
import spring.road.aop.aspectj.AspectJBeforeAdvise;
import spring.road.aop.aspectj.AspectJExpressionPointcut;
import spring.road.beans.config.BeansDefinitionConstants;
import spring.road.beans.config.ConstructorArgument;
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
        List<Element> pointcuts = aspectElement.elements(POINTCUT);
        for (Element pointcutElement : pointcuts) {
            parsePointcut(pointcutElement, registry);
        }
    }

    /**
     * 将切入点表达式 也封装成 BeanDefinitation
     *
     * @param pointcutElement
     * @param registry
     */
    private BeanDefinition parsePointcut(Element pointcutElement, BeanDefinitionRegistry registry) {
        String id = pointcutElement.attributeValue(ID);
        String expression = pointcutElement.attributeValue(EXPRESSION);

        GenericBeanDefinition pointcutDefinition = null;

        pointcutDefinition = createPointcutDefinition(expression);
        String pointcutBeanName = id;
        if (StringUtils.isNotEmpty(pointcutBeanName)) {
            registry.beanDefinationRegister(pointcutBeanName, pointcutDefinition);
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(pointcutDefinition, registry);
        }
        return pointcutDefinition;
    }

    /**
     * 生成 advisorDefinition 并自动设置三个构造参数
     *
     * @param aspectName      切面方法 before  after-returning 等 用来生成切面类
     * @param order           切面类编号
     * @param aspectElement   切面类元素
     * @param adviseEle       aop befor  after-retruning等
     * @param registry        ICO注册
     * @param beanDefinitions bean声明
     * @param beanReferences
     * @return
     */
    private GenericBeanDefinition parseAdvice(String aspectName, int order, Element aspectElement, Element adviseEle, BeanDefinitionRegistry registry, List<BeanDefinition> beanDefinitions, List<RuntimeBeanNameReference> beanReferences) {
        //切面方法类第一个参数  方法定位器
        GenericBeanDefinition methodDefinition = new GenericBeanDefinition(MethodLocatingFactory.class);
        methodDefinition.getpropertyValueList().add(new PropertyValue("targetBeanName", aspectName));
        methodDefinition.getpropertyValueList().add(new PropertyValue("methodName", aspectElement.attributeValue("method")));
        methodDefinition.setSynthetic(true);

        // 切面方法第三个参数   切面类对象
        GenericBeanDefinition aspectFactoryDef = new GenericBeanDefinition(AspectInstanceFactory.class);
        aspectFactoryDef.getpropertyValueList().add(new PropertyValue("aspectBeanName", aspectName));
        aspectFactoryDef.setSynthetic(true);
        //切面方法第二个参数
        GenericBeanDefinition adviceDef = createAdviceDefinition(adviseEle, registry, aspectName, order, methodDefinition, aspectFactoryDef,
                beanDefinitions, beanReferences);
        adviceDef.setSynthetic(true);
        //将aop 切面类 注入到IOC bean声明中
        BeanDefinitionReaderUtils.registerWithGeneratedName(adviceDef, registry);
        return adviceDef;
    }

    private GenericBeanDefinition createAdviceDefinition(Element adviseEle, BeanDefinitionRegistry registry, String aspectName, int order, GenericBeanDefinition methodDefinition, GenericBeanDefinition aspectFactoryDef, List<BeanDefinition> beanDefinitions, List<RuntimeBeanNameReference> beanReferences) {
        GenericBeanDefinition adviceDefinition = new GenericBeanDefinition(getAdviceClass(adviseEle));
        adviceDefinition.getpropertyValueList().add(new PropertyValue(ASPECT_NAME_PROPERTY, aspectName));

        ConstructorArgument cav = adviceDefinition.getConstructorArgument();
        cav.addArgumentValue(methodDefinition);
        //创建第二个参数 pointCut
        Object pointcut = parsePointcutProperty(adviseEle);
        if (pointcut instanceof BeanDefinition) {
            cav.addArgumentValue(pointcut);
            beanDefinitions.add((BeanDefinition) pointcut);
        } else if (pointcut instanceof String) {
            RuntimeBeanNameReference pointcutRef = new RuntimeBeanNameReference((String) pointcut);
            cav.addArgumentValue(pointcutRef);
            beanReferences.add(pointcutRef);
        }
        cav.addArgumentValue(aspectFactoryDef);

        return adviceDefinition;
    }

    private boolean isAdviceNode(Element element) {
        String name = element.getName();
        return (BEFORE.equals(name) || AFTER.equals(name) || AFTER_RETURNING_ELEMENT.equals(name) ||
                AFTER_THROWING_ELEMENT.equals(name) || AROUND.equals(name));
    }

    private Object parsePointcutProperty(Element element) {
        if ((element.attribute(POINTCUT) == null) && (element.attribute(POINTCUT_REF) == null)) {
            return null;
        } else if (element.attribute(POINTCUT) != null) {
            String expression = element.attributeValue(POINTCUT);
            GenericBeanDefinition pointcutDefinition = createPointcutDefinition(expression);
            return pointcutDefinition;
        } else if (element.attribute(POINTCUT_REF) != null) {
            String pointcutRef = element.attributeValue(POINTCUT_REF);
            if (StringUtils.isEmpty(pointcutRef)) {
                return null;
            }
            return pointcutRef;
        }
        return null;
    }

    /**
     * 创建pointCut 的 BeanDefinition
     *
     * @param expression
     * @return
     */
    private GenericBeanDefinition createPointcutDefinition(String expression) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition(AspectJExpressionPointcut.class);
        beanDefinition.setScope(BeansDefinitionConstants.SCOPE_ATTRIBUTE);
        beanDefinition.setSynthetic(true);
        beanDefinition.getpropertyValueList().add(new PropertyValue(EXPRESSION, expression));
        return beanDefinition;
    }

    /**
     * 获取切面类
     *
     * @param adviceElement
     * @return
     */
    private Class<?> getAdviceClass(Element adviceElement) {
        String elementName = adviceElement.getName();
        if (BEFORE.equals(elementName)) {
            return AspectJBeforeAdvise.class;
        } else if (AFTER_RETURNING_ELEMENT.equals(elementName)) {
            return AspectJAfterReturningAdvise.class;
        } else if (AFTER_THROWING_ELEMENT.equals(elementName)) {
            return AspectJAfterThrowingAdvise.class;
        } else {
            throw new IllegalArgumentException("Unknown advice kind [" + elementName + "].");
        }
    }
}
