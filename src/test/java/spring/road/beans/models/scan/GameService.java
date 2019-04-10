package spring.road.beans.models.scan;

import spring.road.beans.annation.AutoWired;
import spring.road.beans.models.Person;
import spring.road.stereotype.Component;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/8.
 */
@Component(value = "gameService")
public class GameService {
    @AutoWired
    private Person person;

    public Person getPerson() {
        return person;
    }
}
