package spring.road.context.factory;

import com.sun.org.apache.regexp.internal.RE;
import javafx.beans.binding.ObjectExpression;
import jdk.nashorn.internal.objects.NativeUint8Array;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import spring.road.beans.config.*;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;
import spring.road.beans.definition.GenericBeanDefinition;
import spring.road.beans.exception.BeanDefinitionException;
import spring.road.beans.support.AnnotationBeanNameGenerator;
import spring.road.beans.utils.ClassUtils;
import spring.road.context.annotations.ScannedGenericBeanDefinition;
import spring.road.context.io.Resource;
import spring.road.core.io.support.PackageResourceLoader;
import spring.road.core.type.classreading.MataDataReader;
import spring.road.core.type.classreading.SimpleMataDataReader;
import spring.road.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
@Slf4j
public class XmlBeanDefaultReader {

    private BeanDefinitionRegistry registry;

    public XmlBeanDefaultReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 加载配置文件 根据文件读取bean声明
     *
     * @param resource
     */
    public void loadBeanDifinitions(Resource resource) {
        SAXReader reader = new SAXReader();
        InputStream is = null;
        try {
            is = resource.getInputStream();
            Document doc = reader.read(is);
            Element root = doc.getRootElement();
            Iterator<Element> elements = root.elementIterator();
            while (elements.hasNext()) {
                Element nextEle = elements.next();
                //获取元素的命名空间
                String namespaceURI = nextEle.getNamespaceURI();
                if (this.isDefaultNamespace(namespaceURI)) {
                    parseDefaultElement(nextEle);//加载<bean></bean>
                } else if (BeansDefinitionConstants.CONTEXT_NAMESPACE_URI.equals(namespaceURI))
                    parseComponentElement(nextEle); //例如<context:component-scan>
            }
        } catch (Exception e) {
            throw new BeanDefinitionException("Create beanDefinition Fail!", e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public boolean isDefaultNamespace(String namespaceUri) {
        return (StringUtils.isEmpty(namespaceUri) || BeansDefinitionConstants.BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    /**
     * 当使用xml 的<bean><bean/>配置时由它处理
     *
     * @param ele
     */
    private void parseDefaultElement(Element ele) {
        //获取bean的name
        String beanName = ele.attributeValue(BeansDefinitionConstants.ID_ATTRIBUTE);
        //获取className
        String className = ele.attributeValue(BeansDefinitionConstants.CLASS_ATTRIBUTE);
        BeanDefinition beanDefinition = new GenericBeanDefinition(beanName, className);
        //设置类的作用域
        if (ele.attributeValue(BeansDefinitionConstants.SCOPE_ATTRIBUTE) != null) {
            beanDefinition.setScope(ele.attributeValue(BeansDefinitionConstants.SCOPE_ATTRIBUTE));
        }
        //解析构造函数 并进行值转换
        parseConstructorArgElements(ele, beanDefinition);
        //解析属性值   并进行值转换
        parsePropertyElement(ele, beanDefinition);
        registry.beanDefinationRegister(beanName, beanDefinition);
    }

    /**
     * 当使用<context:component-scan> 时 由它处理
     *
     * @param ele
     */
    private void parseComponentElement(Element ele) {
        String location = ele.attributeValue(BeansDefinitionConstants.BASE_PACKAGE_ATTRIBUTE);
        location = ClassUtils.convertClassNameToResourcePath(location);
        String[] basePackages = StringUtils.split(location, ",");
        for (int i = 0; i < basePackages.length; i++) {
            PackageResourceLoader resourceLoader = new PackageResourceLoader();
            Resource[] resources = resourceLoader.getResources(basePackages[i]);
            for (Resource resource : resources) {
                try {
                    MataDataReader reader = new SimpleMataDataReader(resource);
                    if (reader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(reader.getAnnotationMetadata());
                        AnnotationBeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();
                        String beanName = nameGenerator.generateBeanName(sbd, registry);
                        sbd.setBeanName(beanName);
                        registry.beanDefinationRegister(beanName, sbd);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取bean声明的constructor-arg 属性
     *
     * @param nextEle
     * @param beanDefinition
     */
    private void parseConstructorArgElements(Element nextEle, BeanDefinition beanDefinition) {
        //获取当前元素下 通过迭代 获取所有 构造参数并进行转换
        Iterator<Element> iterator = nextEle.elementIterator(BeansDefinitionConstants.CONSTRUCTOR_ARG_ELEMENT);
        while (iterator.hasNext()) {
            Element element = iterator.next();
            parseConstructorArgElement(element, beanDefinition);
        }
    }

    /**
     * 解析构造参数  放入 构造参数类中
     *
     * @param ele
     * @param bd
     */
    public void parseConstructorArgElement(Element ele, BeanDefinition bd) {
        String name = ele.attributeValue(BeansDefinitionConstants.NAME_ATTRIBUTE);
        //获取构造参数属性值 并转成对应 运行时中间数据类型
        Object value = parseEleValue(ele);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(name, value);
        bd.getConstructorArgument().addArgumentValue(valueHolder);
    }

    /**
     * 读取bean下的属性放入bean声明集合中
     *
     * @param element
     * @param beanDefinition
     */
    public void parsePropertyElement(Element element, BeanDefinition beanDefinition) {
        //在这里设置属性
        Iterator<Element> propsIteraror = element.elementIterator(BeansDefinitionConstants.CLASS_PROPERTY);
        while (propsIteraror.hasNext()) {
            Element ele = propsIteraror.next();
            String eleName = ele.attributeValue(BeansDefinitionConstants.NAME_ATTRIBUTE);
            if (StringUtils.isEmpty(eleName)) {
                log.error("Tag 'property' must have a 'name' attribute");
                return;
            }
            Object value = parseEleValue(ele);
            beanDefinition.getpropertyValueList().add(new PropertyValue(eleName, value));
        }
    }

    /**
     * 将属性值转换成相应类型 放入 PropertyValue中
     *
     * @param element
     * @return
     */
    public Object parseEleValue(Element element) {
        //再获取value 或者 ref的数值
        boolean isRefType = (element.attributeValue(BeansDefinitionConstants.REF_ATTRIBUTE) != null);
        boolean isValueType = (element.attributeValue(BeansDefinitionConstants.VALUE_ATTRIBUTE) != null);
        if (isRefType) {
            RuntimeBeanNameReference reference = new RuntimeBeanNameReference(element.attributeValue(BeansDefinitionConstants.REF_ATTRIBUTE));
            return reference;
        } else if (isValueType) {
            TypedStringValue valueHolder = new TypedStringValue(element.attributeValue(BeansDefinitionConstants.VALUE_ATTRIBUTE));
            return valueHolder;
        } else {
            throw new RuntimeException(element.attributeValue(BeansDefinitionConstants.NAME_ATTRIBUTE) + " must specify a ref or value");

        }
    }
}
