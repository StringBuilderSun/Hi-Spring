package spring.road.context.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public interface Resource {
    /**
     * 通过配置文件路径获取资源文件流
     * @return
     */
    InputStream getInputStream() throws FileNotFoundException;

    /**
     * 获取文件描述符
     * @return
     */
    String getDescriptions();
}
