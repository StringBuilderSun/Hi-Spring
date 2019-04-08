package spring.road.beans.factory;

import org.junit.Assert;
import org.junit.Test;
import spring.road.context.io.ClassPathResource;
import spring.road.context.io.FileSystemResource;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public class ResourceTest {

    @Test
    public void classResourceTest() throws FileNotFoundException {
        ClassPathResource resource = new ClassPathResource("spring/spring-context.xml", null);
        InputStream stream = resource.getInputStream();
        Assert.assertEquals("spring/spring-context.xml", resource.getDescriptions());
        Assert.assertNotNull(stream);
    }

    @Test
    public void fileSystemResourceTest() throws FileNotFoundException {
//        String fileName = "D:\\ProjectWorkSpace\\Hi-Spring\\src\\main\\resources\\spring\\spring-context.xml";
//        FileSystemResource resource = new FileSystemResource(fileName);
//        InputStream stream = resource.getInputStream();
//        Assert.assertEquals("D:\\ProjectWorkSpace\\Hi-Spring\\src\\main\\resources\\spring\\spring-context.xml", resource.getDescriptions());
//        Assert.assertNotNull(stream);

    }
}
