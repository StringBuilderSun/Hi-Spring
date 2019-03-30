package spring.road.beans.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2019/3/29.
 */
@Setter
@Getter
@AllArgsConstructor
public class BeanDefinition {
    private String beanName;
    private String className;

}
