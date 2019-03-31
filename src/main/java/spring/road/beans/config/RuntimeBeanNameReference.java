package spring.road.beans.config;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public class RuntimeBeanNameReference {

    private final String beanName;

    private Object source;


    /**
     * Create a new RuntimeBeanNameReference to the given bean name.
     *
     * @param beanName name of the target bean
     */
    public RuntimeBeanNameReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return this.beanName;
    }

    /**
     * Set the configuration source {@code Object} for this metadata element.
     * <p>The exact type of the object will depend on the configuration mechanism used.
     */
    public void setSource(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return this.source;
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RuntimeBeanNameReference)) {
            return false;
        }
        RuntimeBeanNameReference that = (RuntimeBeanNameReference) other;
        return this.beanName.equals(that.beanName);
    }

    public String toString() {
        return '<' + getBeanName() + '>';
    }

}
