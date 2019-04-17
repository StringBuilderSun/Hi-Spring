package spring.road.beans.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2019/3/29.
 */
@Getter
@Setter
@Slf4j
public class BeanService {
    private UserDao mapper;
    private String name;
    private boolean sex;
    public void playGames() {
        log.info("person-name:nobody play games");
    }
}
