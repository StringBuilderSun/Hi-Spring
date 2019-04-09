package spring.road.beans.ResourceTest;

import org.junit.Assert;
import org.junit.Test;
import spring.road.context.io.Resource;
import spring.road.core.io.support.PackageResourceLoader;

/**
 * 验证扫描一个包下的所有文件
 * Created by lijinpeng on 2019/4/9.
 */
public class ScanResourceTest {

    @Test
    public void scanPackTest() {
        PackageResourceLoader loader = new PackageResourceLoader(this.getClass().getClassLoader());
        Resource[] resources = loader.getResources("spring.road.beans.models");
        //测试时候 目录下只有五个类  的字节码  断言 以后的文件数会大于5
        Assert.assertTrue(resources.length>=5);
    }
}
