package spring.road.beans;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {BeanFactoryTest.class,
                ResourceTest.class,ApplicationConetxTest.class
        }
)
public class HiSpringTest {
}
