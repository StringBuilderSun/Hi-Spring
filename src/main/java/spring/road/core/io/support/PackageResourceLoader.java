package spring.road.core.io.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import spring.road.beans.utils.Assert;
import spring.road.beans.utils.ClassUtils;
import spring.road.context.io.FileSystemResource;
import spring.road.context.io.Resource;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 包解析工具类 可以解析一个路径下所有的文件
 * Created by lijinpeng on 2019/4/9.
 */
@Slf4j
public class PackageResourceLoader {

    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ResourceLoader must not be null");
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public Resource[] getResources(String basePackage) {
        log.debug("扫描包获取字节码文件:{}", basePackage);
        if (StringUtils.isEmpty(basePackage)) {
            return new Resource[0];
        }
        //将包名转换成目录名
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        URL resource = this.classLoader.getResource(location);
        File rootDir = new File(resource.getFile());
        //获取包目录下所有文件
        Set<File> fileSets = retrieveMatchingFiles(rootDir);
        Resource[] resources = new Resource[fileSets.size()];
        int i = 0;
        for (File file : fileSets) {
            resources[i++] = new FileSystemResource(file);
        }
        return resources;
    }

    /**
     * 解析包路径下的所有文件
     *
     * @param rootDir
     * @return
     */
    private Set<File> retrieveMatchingFiles(File rootDir) {
        if (!rootDir.exists()) {
            log.debug("跳过该包扫描，因为要扫描的包:{} 不存在！", rootDir.getAbsolutePath());
            return Collections.EMPTY_SET;
        }
        if (!rootDir.isDirectory()) {
            log.debug("跳过该包扫描，因为要扫描的包:{} 必须是一个路径！", rootDir.getAbsolutePath());
            return Collections.EMPTY_SET;
        }
        if (!rootDir.canRead()) {
            log.debug("跳过该包扫描，因为要扫描的包:{} 无可读权限！", rootDir.getAbsolutePath());
            return Collections.EMPTY_SET;
        }
        Set<File> result = new LinkedHashSet<File>();
        doRetrieveMatchingFiles(rootDir, result);
        return result;
    }

    /**
     * 递归读取目录下的所有文件 放入到集合中
     *
     * @param rootDir
     * @param result
     */
    private void doRetrieveMatchingFiles(File rootDir, Set<File> result) {
        File[] fileLists = rootDir.listFiles();
        if (fileLists == null || fileLists.length == 0) {
            log.debug("要扫描的包下没有发现任何文件！");
            return;
        }
        for (File file : fileLists) {
            if (file.isDirectory()) {
                if (!file.canRead()) {
                    log.debug("跳过该包扫描，因为要扫描的包:{} 无可读权限！", rootDir.getAbsolutePath());
                }
                doRetrieveMatchingFiles(file, result);
            } else {
                result.add(file);
            }
        }
    }

}
