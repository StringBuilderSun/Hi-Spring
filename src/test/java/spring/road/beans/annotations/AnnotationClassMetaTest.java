package spring.road.beans.annotations;

import jdk.internal.org.objectweb.asm.ClassReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spring.road.context.io.ClassPathResource;
import spring.road.core.type.classreading.AnnotationMetadataReadingVisitor;
import spring.road.core.type.classreading.ClassMetadataReadingVisitor;

import java.io.IOException;

/**
 * 读取类的字节码文件 获取类的注解以及注解类的属性
 * User: lijinpeng
 * Created by Shanghai on 2019/4/8.
 */
public class AnnotationClassMetaTest {

    private ClassReader reader;

    @Before
    public void init() throws IOException {
        ClassPathResource resource = new ClassPathResource("spring/road/beans/models/scan/GameService.class");
        reader = new ClassReader(resource.getInputStream());
    }

    /**
     * 首先验证ClassMetadataReadingVisitor 符合要求
     *
     * @throws IOException
     */
    @Test
    public void classMetaTest() {

        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        Assert.assertEquals("spring.road.beans.models.scan.GameService", visitor.getClassName());
        Assert.assertEquals("spring.road.beans.models.scan.impl.Boss", visitor.getSuperClassName());
        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    /**
     * 验证获取注解属性是否工作正常
     */
    @Test
    public void annotationMetaTest() {
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        Assert.assertEquals(1,visitor.getAnnotationTypes().size());
        Assert.assertNotNull(visitor.getAnnotationAttributes("spring.road.stereotype.Component"));
        Assert.assertEquals(1,visitor.getAnnotationAttributes("spring.road.stereotype.Component").size());
        Assert.assertEquals("gameService",visitor.getAnnotationAttributes("spring.road.stereotype.Component").get("value"));
        Assert.assertTrue(visitor.hasAnnotation("spring.road.stereotype.Component"));
    }
}
