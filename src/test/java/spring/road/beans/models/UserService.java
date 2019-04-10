package spring.road.beans.models;

import spring.road.beans.annation.Autowired;
import spring.road.stereotype.Component;

/**
 * Created by lijinpeng on 2019/4/8.
 */

@Component(value = "userService")
public class UserService {
    @Autowired
    private Person person;

}
