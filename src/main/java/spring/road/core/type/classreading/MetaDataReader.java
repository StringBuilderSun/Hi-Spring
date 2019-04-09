package spring.road.core.type.classreading;

import spring.road.context.io.Resource;
import spring.road.core.type.AnnotationMetadata;
import spring.road.core.type.ClassMetadata;

/**
 * 对外提供 访问类的元数据接口
 * Created by lijinpeng on 2019/4/9.
 */
public interface MetaDataReader {
    /**
     * 获取类的资源文件
     * @return
     */
    Resource getResource();

    /**
     * 获取类的信息
     * @return
     */
    ClassMetadata getClassMetadata();

    /**
     * 获取类上注解信息
     * @return
     */
    AnnotationMetadata getAnnotationMetadata();
}
