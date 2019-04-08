package spring.road.beans;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import spring.road.beans.factory.ApplicationConetxTest;
import spring.road.beans.factory.BeanFactoryTest;
import spring.road.beans.factory.ResourceTest;
import spring.road.beans.factory.SingleBeanFactoryTest;
import spring.road.beans.setter.ConstructorSetTest;
import spring.road.beans.setter.PropertieySetTest;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {BeanFactoryTest.class,
                ResourceTest.class, ApplicationConetxTest.class
                , SingleBeanFactoryTest.class, PropertieySetTest.class
                ,ConstructorSetTest.class
        }
)
public class HiSpringTest {
}
