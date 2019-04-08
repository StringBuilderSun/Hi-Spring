package spring.road.beans.springAsm;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import org.junit.Test;
import spring.road.asm.DefaultAnnationClassVistor;
import spring.road.asm.DefaultClassVisitor;
import spring.road.context.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by lijinpeng on 2019/4/8.
 */
public class SpringAsmTest {
    @Test
    public void asmTest() throws IOException {
        ClassPathResource resource=new ClassPathResource("spring/road/beans/models/UserService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());
        ClassVisitor visitor=new DefaultAnnationClassVistor();
        reader.accept(visitor,ClassReader.SKIP_DEBUG);

    }
}
