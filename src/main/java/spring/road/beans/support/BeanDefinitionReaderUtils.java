package spring.road.beans.support;

import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;
import spring.road.beans.definition.GenericBeanDefinition;
import spring.road.beans.exception.BeanDefinitionStoreException;
import spring.road.beans.utils.StringUtils;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/22.
 */
public class BeanDefinitionReaderUtils {

    public static final String GENERATED_BEAN_NAME_SEPARATOR = "#";


    public static String generateBeanName(
            BeanDefinition definition, BeanDefinitionRegistry registry, boolean isInnerBean)
            throws BeanDefinitionStoreException {
        String generatedBeanName = definition.getBeanClassName();
        if (generatedBeanName == null) {

        }
        if (!StringUtils.hasText(generatedBeanName)) {
            throw new BeanDefinitionStoreException("Unnamed bean definition specifies neither " +
                    "'class' nor 'parent' nor 'factory-bean' - can't generate bean name");
        }
        String id = generatedBeanName;
        if (isInnerBean) {
            id = generatedBeanName + GENERATED_BEAN_NAME_SEPARATOR + Integer.toHexString(System.identityHashCode(definition));
        } else {
            int counter = -1;
            while (counter == -1 || (registry.getBeanDefinition(id) != null)) {
                counter++;
                id = generatedBeanName + GENERATED_BEAN_NAME_SEPARATOR + counter;
            }
        }
        return id;
    }


    public static String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry)
            throws BeanDefinitionStoreException {

        return generateBeanName(beanDefinition, registry, false);
    }


    public static String registerWithGeneratedName(GenericBeanDefinition definition, BeanDefinitionRegistry registry) {
        String generatedName = generateBeanName(definition, registry, false);
        registry.beanDefinationRegister(generatedName, definition);
        return generatedName;
    }
}
