package spring.road.beans.utils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/4/6.
 */
public class JavaSsiteUtils {
    /**
     * 获取方法的参数变量名称
     * javassite
     * @param classname
     * @return
     */
    public static String[] getMethodVariableName(String classname, int paramsLength) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(classname);
            CtConstructor localctor = null;
            CtConstructor[] constructors = cc.getConstructors();
            for (int i = 0; i < constructors.length; i++) {
                if (constructors[i].getParameterTypes().length == paramsLength) {
                    localctor = constructors[i];
                }
            }
            MethodInfo methodInfo = localctor.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            String[] paramNames = new String[paramsLength];
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr != null) {
                int pos = Modifier.isStatic(localctor.getModifiers()) ? 0 : 1;
                for (int i = 0; i < paramNames.length; i++) {
                    paramNames[i] = attr.variableName(i+pos);
                }
                return paramNames;
            }
        } catch (Exception e) {
            System.out.println("getMethodVariableName fail " + e);
        }
        return null;
    }

}
