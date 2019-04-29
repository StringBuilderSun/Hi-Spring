package spring.road.beans.utils;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import spring.road.beans.config.ConstructorArgument;

import java.util.List;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/4/6.
 */
public class JavaSsiteUtils {
//    /**
//     * 获取方法的参数变量名称
//     * javassite
//     *
//     * @param classname
//     * @return
//     */
//    public static boolean getMethodVariableName(String classname, ConstructorArgument args,Object[] useArgs) {
//        try {
//            ClassPool pool = ClassPool.getDefault();
//            CtClass cc = pool.get(classname);
//            CtConstructor localctor = null;
//            CtConstructor[] constructors = cc.getConstructors();
//            for (int i = 0; i < constructors.length; i++) {
//                CtClass[] ctConstructors = constructors[i].getParameterTypes();
//                if (ctConstructors.length == args.getArgumentCount()) {
//                    //构造函数参数个数匹配后   核对参数类型是否对的上
//                    if (isMatchSuitAbleConstructorsByType(args,constructors[i])) {
//
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("getMethodVariableName fail " + e);
//        }
//    return false;
//    }
//
//    /**
//     * 判断构造函数是否匹配
//     *
//     * @param args
//     * @param constructor
//     * @return
//     */
//    public static boolean isMatchSuitAbleConstructorsByType(ConstructorArgument args, CtConstructor constructor) throws NotFoundException {
//        String[] methodParamterNames = getMethodSign(constructor, args);
//        CtClass[] parameterTypes = constructor.getParameterTypes();
//        for (int i = 0; i < parameterTypes.length; i++) {
//
//        }
//        return false;
//    }
//
//    public static String[] getMethodSign(CtConstructor localctor, ConstructorArgument args) {
//        MethodInfo methodInfo = localctor.getMethodInfo();
//        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
//        String[] paramNames = new String[args.getArgumentCount()];
//        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
//        if (attr != null) {
//            int pos = Modifier.isStatic(localctor.getModifiers()) ? 0 : 1;
//            for (int index = 0; index < paramNames.length; index++) {
//                paramNames[index] = attr.variableName(index + pos);
//            }
//            return paramNames;
//        }
//        return null;
//    }
}
