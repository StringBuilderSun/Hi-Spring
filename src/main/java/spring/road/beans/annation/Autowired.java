package spring.road.beans.annation;


import java.lang.annotation.*;

/**
 * Created by lijinpeng on 2019/4/8.
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    boolean required() default true;
}
