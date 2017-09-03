package me.ianhe.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.ianhe.dao.SysAuthMapper;
import me.ianhe.db.entity.SysAuth;
import me.ianhe.db.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * @author iHelin
 * @since 2017/8/30 18:03
 */
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private SysAuthMapper sysAuthMapper;
    private static PathMatcher pathMatcher = new AntPathMatcher();
    private static Map<String, Collection<ConfigAttribute>> resourceMap = Maps.newHashMap();

    /**
     * 刷新系统权限
     *
     * @author iHelin
     * @since 2017/8/31 22:19
     */
    public void loadAuthAndRole() {
        resourceMap.clear();
        //TODO
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
            String authURL = ite.next();
            if (pathMatcher.match(authURL, url)) {
                return resourceMap.get(authURL);
            }
        }
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * Spring容器启动时自动调用的, 返回所有权限的集合.
     *
     * @author iHelin
     * @since 2017/9/1 15:04
     */
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allConfigAttributes = Sets.newHashSet();
        List<SysAuth> sysAuthList = sysAuthMapper.selectSysAuth();
        for (SysAuth sysAuth : sysAuthList) {
            List<SysRole> sysRoles = sysAuth.getSysRoles();
            List<ConfigAttribute> configAttributes = Lists.newArrayList();
            for (SysRole sysRole : sysRoles) {
                configAttributes.add(new SecurityConfig(sysRole.getName()));
            }
            allConfigAttributes.addAll(configAttributes);
            resourceMap.put(sysAuth.getAuth(), configAttributes);
        }
        return allConfigAttributes;
    }
}
