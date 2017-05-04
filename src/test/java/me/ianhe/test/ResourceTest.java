package me.ianhe.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * Created by iHelin on 17/5/3 19:51.
 */
public class ResourceTest {

    @Test
    public void resource() throws IOException {
        Resource resource = new ClassPathResource("access_token.yml");
        System.out.println(resource.getURI());
        System.out.println(resource.getURL());
        System.out.println(resource.getFilename());
        System.out.println(resource.getDescription());
    }

    @Test
    public void getResources() throws Throwable{
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:*.xml");
        Assert.assertNotNull(resources);
        for (Resource resource : resources) {
            System.out.println(resource.getDescription());
        }
    }

}
