package spring.road.beans.models;

import lombok.Getter;
import lombok.Setter;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/4/5.
 */
@Setter
@Getter
public class Person {
    private String name;
    private int age;
    private boolean sex;
    private UserDao userDao;

    public Person(String name, int age, boolean sex, UserDao userDao) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.userDao = userDao;
    }


    public Person(UserDao userDao) {
        this.userDao = userDao;
        this.age = 27;
        this.name = "李金鹏";
        this.sex = false;
    }
}
