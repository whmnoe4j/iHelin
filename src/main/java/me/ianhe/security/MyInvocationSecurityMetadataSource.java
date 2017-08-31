package me.ianhe.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/8/30 18:03
 */
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired(required = false)
    private SysAuthMapper sysAuthMapper;

    private PathMatcher pathMatcher = new AntPathMatcher();
    private static Map<String, Collection<ConfigAttribute>> resourceMap = Maps.newHashMap();

    public MyInvocationSecurityMetadataSource() {
//        loadAuthAndRole();
    }

    /**
     * 刷新系统权限 - 加载所有url和权限的对应关系
     *
     * @author iHelin
     * @since 2017/8/31 22:19
     */
    @PostConstruct
    public void loadAuthAndRole() {
        resourceMap.clear();
        List<SysAuth> sysAuths = sysAuthMapper.selectSysAuth();
        for (SysAuth sysAuth : sysAuths) {
            Collection<ConfigAttribute> configAttributes = Lists.newArrayList();
            List<SysRole> sysRoles = sysAuth.getSysRoles();
            for (SysRole sysRole : sysRoles) {
                configAttributes.add(new SecurityConfig(sysRole.getName()));
            }
            resourceMap.put(sysAuth.getAuth(), configAttributes);
        }
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

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
