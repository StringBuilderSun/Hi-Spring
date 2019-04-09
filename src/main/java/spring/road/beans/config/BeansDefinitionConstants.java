package spring.road.beans.config;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public class BeansDefinitionConstants {
    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String SCOPE_ATTRIBUTE = "scope";
    /**
     * 类属性
     */
    public static final String CLASS_PROPERTY = "property";

    public static final String NAME_ATTRIBUTE = "name";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    //构造函数
    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
    //要扫描的基础包路径
    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
}
