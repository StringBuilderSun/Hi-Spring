package spring.road.beans;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import spring.road.beans.annotations.AnnotationBeanDefinedTest;
import spring.road.beans.annotations.AnnotationClassMetaTest;
import spring.road.beans.annotations.AutowireAllFinishTest;
import spring.road.beans.annotations.SimpleAnnotationReaderTest;
import spring.road.beans.factory.ApplicationConetxTest;
import spring.road.beans.factory.BeanFactoryTest;
import spring.road.beans.ResourceTest.ResourceTest;
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
                , ConstructorSetTest.class, AnnotationClassMetaTest.class,
                SimpleAnnotationReaderTest.class, AnnotationBeanDefinedTest.class
                , AutowireAllFinishTest.class
        }
)
public class HiSpringTest {
}
