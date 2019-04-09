package spring.road.context.annotations;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;
import spring.road.beans.exception.BeanDefinitionException;
import spring.road.beans.support.AnnotationBeanNameGenerator;
import spring.road.beans.support.BeanNameGenerator;
import spring.road.beans.utils.ClassUtils;
import spring.road.context.io.Resource;
import spring.road.core.io.support.PackageResourceLoader;
import spring.road.core.type.classreading.MetaDataReader;
import spring.road.core.type.classreading.SimpleMetaDataReader;
import spring.road.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 包扫描解析工具类
 * User: lijinpeng
 * Created by Shanghai on 2019/4/9.
 */
@Slf4j
public class ClassPathBeanDefinitionScanner {
    /**
     * 注册中心
     */
    private BeanDefinitionRegistry registry;
    /**
     * beanName 生成器
     */
    private BeanNameGenerator nameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String basePackage) {
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        //获取配置的所有包集合
        String[] basePackages = StringUtils.split(location, ",");
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>(4);
        for (String pack : basePackages) {
            //获取每个包下的所有使用了注解的bean声明
            Set<BeanDefinition> candidates = findCandidateComponents(pack);
            for (BeanDefinition candidate : candidates) {
                beanDefinitions.add(candidate);
                registry.beanDefinationRegister(candidate.getBeanName(), candidate);

            }
        }
        return beanDefinitions;
    }

    /**
     * 查找该包下的所有资源文件 生成BeanDefinition
     *
     * @param basePack
     * @return
     */
    private Set<BeanDefinition> findCandidateComponents(String basePack) {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources(basePack);
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>(4);
        for (Resource resource : resources) {
            try {
                MetaDataReader reader = new SimpleMetaDataReader(resource);
                if (reader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                    //如果使用了Component 注解 则扫描进包
                    ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(reader.getAnnotationMetadata());
                    String beanName = nameGenerator.generateBeanName(sbd, registry);
                    sbd.setBeanName(beanName);
                    beanDefinitions.add(sbd);
                }
            } catch (IOException e) {
                log.error("在包下生成BeanDefinition失败,包名:{}", e, basePack);
                throw new BeanDefinitionException("创建ScannedGenericBeanDefinition异常！", e);
            }
        }
        return beanDefinitions;
    }
}
