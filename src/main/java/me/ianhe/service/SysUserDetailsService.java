package me.ianhe.service;

import com.google.common.collect.Lists;
import me.ianhe.dao.SysRoleMapper;
import me.ianhe.dao.UserMapper;
import me.ianhe.db.entity.SysRole;
import me.ianhe.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/8/29 17:36
 */
@Service
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GrantedAuthority> authorities = getGrantedAuthorities(user);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }

    /**
     * 获取用户的所有权限
     *
     * @author iHelin
     * @since 2017/8/30 20:23
     */
    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<SysRole> sysRoles = sysRoleMapper.getUserRoles(user.getId());
        List<GrantedAuthority> authorities = Lists.newArrayList();
        for (SysRole role : sysRoles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
