package spring.road.context.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public class FileSystemResource implements Resource {
    private File file;

    public FileSystemResource(String configPath) {
        file = new File(configPath);
    }

    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public String getDescriptions() {
        return  file.getAbsolutePath();
    }
}
