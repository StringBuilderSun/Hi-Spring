package spring.road.beans.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2019/3/29.
 */
@Getter
@Setter
public class BeanService {
    private UserDao mapper;
    private String name;
    private boolean sex;

}
