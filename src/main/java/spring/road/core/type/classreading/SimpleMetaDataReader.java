package spring.road.core.type.classreading;

import jdk.internal.org.objectweb.asm.ClassReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import spring.road.context.io.Resource;
import spring.road.core.type.AnnotationMetadata;
import spring.road.core.type.ClassMetadata;

import java.io.*;

/**
 * 封装访问类的注解信息的实现类
 * Created by lijinpeng on 2019/4/9.
 */
@Slf4j
public class SimpleMetaDataReader implements MetaDataReader {
    /**
     * 类的字节码文件
     */
    private Resource resource;
    /**
     * 类的基本信息
     */
    private ClassMetadata classMetadata;
    /**
     * 类的注解信息
     */
    private AnnotationMetadata annotationMetadata;

    /**
     * 读取类的资源文件 完成类字节码文件的解析
     *
     * @param resource
     * @throws IOException
     */
    public SimpleMetaDataReader(Resource resource) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(resource.getInputStream());
            ClassReader reader = new ClassReader(inputStream);
            AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
            reader.accept(visitor, ClassReader.SKIP_DEBUG);
            this.classMetadata = visitor;
            this.annotationMetadata = visitor;
            this.resource = resource;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

    }

    public Resource getResource() {
        return this.resource;
    }

    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }
}
