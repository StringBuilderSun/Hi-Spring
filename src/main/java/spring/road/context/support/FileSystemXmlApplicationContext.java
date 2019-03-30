package spring.road.context.support;

import spring.road.context.factory.AbstractApplicationContext;
import spring.road.context.io.FileSystemResource;
import spring.road.context.io.Resource;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {
    public FileSystemXmlApplicationContext(String configPath) {
        super(configPath);
    }

    public Resource getResource(String configPath) {
        return new FileSystemResource(configPath);
    }
}
