package spring.road.beans.support;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import spring.road.beans.core.BeanFactory;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.exception.BeanCreateException;
import spring.road.beans.exception.BeanDefinitionException;
import spring.road.beans.utils.ClassUtils;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2019/3/29.
 */
public class DefaultBeanFactory implements BeanFactory {
    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";
    /**
     * beans 声明集合
     */
    private Map<String, BeanDefinition> beanDefinitionsMap = new ConcurrentHashMap<String, BeanDefinition>();

    public DefaultBeanFactory(String configFile) {
        super();
        loadBeanDefinitions(configFile);
    }

    public void loadBeanDefinitions(String configFile) {
        SAXReader reader = new SAXReader();
        InputStream is=null;
        try {
            is = this.getClass().getResourceAsStream(configFile);
            Document doc = reader.read(is);
            Element root = doc.getRootElement();
            Iterator<Element> elements = root.elementIterator();
            while (elements.hasNext()) {
                Element nextEle = elements.next();
                //获取bean的name
                String beanName = nextEle.attributeValue(ID_ATTRIBUTE);
                //获取className
                String className = nextEle.attributeValue(CLASS_ATTRIBUTE);
                beanDefinitionsMap.put(beanName, new BeanDefinition(beanName, className));
            }
        } catch (DocumentException e) {
            throw new BeanDefinitionException("Create beanDefinition Fail!", e);
        }finally {
           IOUtils.closeQuietly(is);
        }
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        return beanDefinitionsMap.get(beanId);
    }

    public Object getBean(String beanId) {
        //首先得到bean声明
        BeanDefinition beanDefinition = getBeanDefinition(beanId);
        if (beanDefinition == null) {
            return null;
        }
        ClassLoader classLoader = ClassUtils.getDefaultClassLoad();
        String beanName = beanDefinition.getClassName();
        try {
            Class<?> objectBean = classLoader.loadClass(beanName);
            return objectBean.newInstance();
        } catch (Exception e) {
            throw new BeanCreateException(beanName, e.getMessage(), e);
        }
    }
}
