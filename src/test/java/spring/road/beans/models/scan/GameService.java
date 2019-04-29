package spring.road.beans.models.scan;

import lombok.extern.slf4j.Slf4j;
import spring.road.beans.annation.Autowired;
import spring.road.beans.models.Person;
import spring.road.beans.models.scan.impl.Boss;
import spring.road.stereotype.Component;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/8.
 */
@Component(value = "gameService")
@Slf4j
public class GameService extends Boss {

    @Autowired
    public Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void playGames() {
        log.info("person-name:{} play games", person.getName());
//        throw new Exception("test exception");
    }
}
