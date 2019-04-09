package spring.road.beans.annotations;

import com.sun.xml.internal.bind.v2.model.annotation.AnnotationReader;
import org.junit.Assert;
import org.junit.Test;
import spring.road.context.io.ClassPathResource;
import spring.road.core.type.AnnotationMetadata;
import spring.road.core.type.ClassMetadata;
import spring.road.core.type.classreading.MataDataReader;
import spring.road.core.type.classreading.SimpleMataDataReader;

import java.io.IOException;

/**
 * 验证读取类信息的接口
 * Created by lijinpeng on 2019/4/9.
 */
public class SimpleAnnotationReaderTest {
    @Test
    public void AnnotationReaderTest() throws IOException {
        ClassPathResource resource = new ClassPathResource("spring/road/beans/models/scan/GameService.class");
        MataDataReader reader = new SimpleMataDataReader(resource);
        ClassMetadata classMetadata = reader.getClassMetadata();
        AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
        Assert.assertEquals("spring.road.beans.models.scan.GameService", classMetadata.getClassName());
        Assert.assertEquals("java.lang.Object", classMetadata.getSuperClassName());
        Assert.assertFalse(classMetadata.isAbstract());
        Assert.assertFalse(classMetadata.isFinal());
        Assert.assertFalse(classMetadata.isInterface());
        Assert.assertEquals(0, classMetadata.getInterfaceNames().length);
        Assert.assertEquals(1, annotationMetadata.getAnnotationTypes().size());
        Assert.assertNotNull(annotationMetadata.getAnnotationAttributes("spring.road.stereotype.Component"));
        Assert.assertEquals(1, annotationMetadata.getAnnotationAttributes("spring.road.stereotype.Component").size());
        Assert.assertEquals("gameService", annotationMetadata.getAnnotationAttributes("spring.road.stereotype.Component").get("value"));
        Assert.assertTrue(annotationMetadata.hasAnnotation("spring.road.stereotype.Component"));
    }
}
