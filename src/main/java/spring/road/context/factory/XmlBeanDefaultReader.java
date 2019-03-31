package spring.road.context.factory;

import jdk.nashorn.internal.objects.NativeUint8Array;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import spring.road.beans.config.BeansDefinitionConstants;
import spring.road.beans.config.PropertyValue;
import spring.road.beans.config.RuntimeBeanNameReference;
import spring.road.beans.config.TypedStringValue;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;
import spring.road.beans.definition.GenericBeanDefinition;
import spring.road.beans.exception.BeanDefinitionException;
import spring.road.context.io.Resource;

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
                //获取bean的name
                String beanName = nextEle.attributeValue(BeansDefinitionConstants.ID_ATTRIBUTE);
                //获取className
                String className = nextEle.attributeValue(BeansDefinitionConstants.CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(beanName, className);
                //设置类的作用域
                if (nextEle.attributeValue(BeansDefinitionConstants.SCOPE_ATTRIBUTE) != null) {
                    beanDefinition.setScope(nextEle.attributeValue(BeansDefinitionConstants.SCOPE_ATTRIBUTE));
                }
                parsePropertyElement(nextEle, beanDefinition);
                registry.beanDefinationRegister(beanName, beanDefinition);
            }
        } catch (Exception e) {
            throw new BeanDefinitionException("Create beanDefinition Fail!", e);
        } finally {
            IOUtils.closeQuietly(is);
        }
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
