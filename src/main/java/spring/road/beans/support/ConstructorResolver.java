package spring.road.beans.support;

import lombok.extern.slf4j.Slf4j;
import spring.road.beans.config.ConfigurableBeanFactory;
import spring.road.beans.config.ConstructorArgument;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.exception.BeanCreateException;
import spring.road.beans.utils.JavaSsiteUtils;

import java.lang.reflect.Constructor;

/**
 * 构造函数方法解析器
 * User: StringBuilderSun
 * Created by Shanghai on 2019/4/5.
 */
@Slf4j
public class ConstructorResolver {
    private ConfigurableBeanFactory beanFactory;

    public ConstructorResolver(ConfigurableBeanFactory factory) {
        this.beanFactory = factory;
    }

    /**
     * 将beanDefinaition 中定义的构造参数值 放入创建的bean实例中
     *
     * @param bd
     * @return
     */
    public Object autowireConstructor(BeanDefinition bd) {
        ConstructorArgument args = bd.getConstructorArgument();
        final String beanClass = bd.getBeanClassName();
        //通过反射创建bean实例
        try {
            Class<?> instance = beanFactory.getClassLoader().loadClass(bd.getBeanClassName());
            //获取bean实例的构造函数
            Constructor<?>[] candidates = instance.getConstructors();
            Object[] useArgs;
            BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(beanFactory);
            //遍历类的构造函数集合 获取到在xml配置的bean函数
            for (int i = 0; i < candidates.length; i++) {
                Class<?>[] parameterTypes = candidates[i].getParameterTypes();

                if (parameterTypes.length != args.getArgumentCount()) {
                    continue;
                }
                useArgs = new Object[parameterTypes.length];
                //即使构造函数不是按顺序配置  也可以按照name 去匹配 暂时不支持参数重载
                //按照顺序获取构造函数参数名字  使用javaSsite操作字节码文件获取
                String[] paramNames = JavaSsiteUtils.getMethodVariableName(beanClass, args.getArgumentCount());
                for (int paramIndex = 0; paramIndex < paramNames.length; paramIndex++) {
                    ConstructorArgument.ValueHolder valueHolder = args.getValueHolder(paramNames[paramIndex]);
                    useArgs[paramIndex] = valueResolver.resolveValueIfNecessary(valueHolder.getValue(), parameterTypes[paramIndex]);

                }
                return candidates[i].newInstance(useArgs);
            }

        } catch (Exception e) {
            log.error("执行构造器注入失败！class:{}", e, beanClass);
            throw new BeanCreateException("bean Constructor fail! class=" + beanClass);
        }
        throw new BeanCreateException("No suitable Constructor to find ! class=" + beanClass);
    }


}
