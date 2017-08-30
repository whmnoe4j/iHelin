package me.ianhe.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/8/30 18:03
 */
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private PathMatcher pathMatcher = new AntPathMatcher();
    private static Map<String, Collection<ConfigAttribute>> resourceMap = Maps.newHashMap();

    /**
     * 加载所有url和权限的对应关系
     *
     * @author iHelin
     * @since 2017/8/30 20:00
     */
    public MyInvocationSecurityMetadataSource() {
        Collection<ConfigAttribute> configAttributes = Lists.newArrayList();
        configAttributes.add(new SecurityConfig("ROLE_ADMIN"));
        resourceMap.put("/admin/**", configAttributes);
    }

    /**
     * 返回url对应的所有权限
     *
     * @author iHelin
     * @since 2017/8/30 19:59
     */
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (pathMatcher.match(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
