package spring.road.context.factory;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import spring.road.beans.config.BeansDefinitionConstants;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;
import spring.road.beans.exception.BeanDefinitionException;
import spring.road.context.io.Resource;

import java.io.InputStream;
import java.util.Iterator;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public class XmlBeanDefaultReader {

    private BeanDefinitionRegistry registry;

    public XmlBeanDefaultReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

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
                registry.beanDefinationRegister(beanName, new BeanDefinition(beanName, className));
            }
        } catch (Exception e) {
            throw new BeanDefinitionException("Create beanDefinition Fail!", e);
        }  finally {
            IOUtils.closeQuietly(is);
        }
    }
}
