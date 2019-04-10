package spring.road.beans.models.scan;

import spring.road.beans.annation.Autowired;
import spring.road.beans.models.Person;
import spring.road.beans.models.scan.impl.Boss;
import spring.road.stereotype.Component;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/8.
 */
@Component(value = "gameService")
public class GameService extends Boss {
    @Autowired
    private Person person;

    public Person getPerson() {
        return person;
    }
}
