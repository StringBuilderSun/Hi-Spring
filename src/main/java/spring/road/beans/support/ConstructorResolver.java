package spring.road.beans.support;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import lombok.extern.slf4j.Slf4j;
import spring.road.beans.config.ConstructorArgument;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.exception.BeanCreateException;
import java.lang.reflect.Constructor;


/**
 * 构造函数方法解析器
 * User: StringBuilderSun
 * Created by Shanghai on 2019/4/5.
 */
@Slf4j
public class ConstructorResolver {
    private AbstractBeanFactory beanFactory;

    public ConstructorResolver(AbstractBeanFactory factory) {
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

            //遍历类的构造函数集合 获取到在xml配置的bean函数
            for (int i = 0; i < candidates.length; i++) {
                Class<?>[] parameterTypes = candidates[i].getParameterTypes();

                if (parameterTypes.length != args.getArgumentCount()) {
                    continue;
                }
                useArgs = new Object[parameterTypes.length];
                //即使构造函数不是按顺序配置  也可以按照name 去匹配 暂时不支持参数重载
                //按照顺序获取构造函数参数名字  使用javaSsite操作字节码文件获取
                boolean result = getMethodVariableName(beanClass, args, parameterTypes, useArgs);
                if (result) {
                    return candidates[i].newInstance(useArgs);
                }
            }

        } catch (Exception e) {
            log.error("执行构造器注入失败！class:{}", e, beanClass);
            throw new BeanCreateException("bean Constructor fail! class=" + beanClass);
        }
        throw new BeanCreateException("No suitable Constructor to find ! class=" + beanClass);
    }


    /**
     * 获取匹配的构造函数
     * @param classname 类的全限定名
     * @param args beandefination的参数
     * @param parameterTypes 构造函数参数类型
     * @param useArgs 使用的参数值
     * @return
     */
    public boolean getMethodVariableName(String classname, ConstructorArgument args, Class<?>[] parameterTypes, Object[] useArgs) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(classname);
            CtConstructor[] constructors = cc.getConstructors();
            for (int i = 0; i < constructors.length; i++) {
                CtClass[] ctConstructors = constructors[i].getParameterTypes();
                if (ctConstructors.length == args.getArgumentCount()) {
                    //构造函数参数个数匹配后   核对参数类型是否对的上
                    return isMatchSuitAbleConstructorsByType(args, constructors[i], parameterTypes,useArgs);
                }
            }
        } catch (Exception e) {
            log.error("getMethodVariableName fail ", e);
        }
        return false;
    }

    /**
     * 判断构造函数是否匹配
     *
     * @param args
     * @param constructor
     * @return
     */
    public boolean isMatchSuitAbleConstructorsByType(ConstructorArgument args, CtConstructor constructor,Class<?>[] parameterTypes, Object[] useArgs) {
        try {
            BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(beanFactory);
            String[] methodParamterNames = getMethodSign(constructor, args);
            for (int i = 0; i < parameterTypes.length; i++) {
                //构造函数当前参数名字
                String argName = methodParamterNames[i];
                Object oldValue;
                if (args.getValueHolder(argName) != null) {
                    oldValue = args.getValueHolder(argName).getValue();
                } else {
                    oldValue = args.getArgumentValues().get(i).getValue();
                }
                Object realValue = valueResolver.resolveValueIfNecessary(oldValue,parameterTypes[i]);
                useArgs[i] = realValue;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public String[] getMethodSign(CtConstructor localctor, ConstructorArgument args) {
        MethodInfo methodInfo = localctor.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        String[] paramNames = new String[args.getArgumentCount()];
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr != null) {
            int pos = Modifier.isStatic(localctor.getModifiers()) ? 0 : 1;
            for (int index = 0; index < paramNames.length; index++) {
                paramNames[index] = attr.variableName(index + pos);
            }
            return paramNames;
        }
        return null;
    }


}
