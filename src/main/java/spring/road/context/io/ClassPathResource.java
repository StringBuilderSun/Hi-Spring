package spring.road.context.io;

import spring.road.beans.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 加载类路径下的配置文件
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public class ClassPathResource implements Resource {
    private String configPath;
    private ClassLoader classLoader;

    public ClassPathResource(String configPath) {
        this(configPath, null);
    }

    public ClassPathResource(String configPath, ClassLoader classLoader) {
        this.configPath = configPath;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    public InputStream getInputStream() throws FileNotFoundException {
        InputStream is = this.classLoader.getResourceAsStream(this.configPath);
        if (is == null) {
            throw new FileNotFoundException(configPath + " cannot be opened");
        }
        return is;


    }

    public String getDescriptions() {
        return configPath;
    }
}
