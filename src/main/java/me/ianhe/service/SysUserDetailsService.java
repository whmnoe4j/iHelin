package me.ianhe.service;

import me.ianhe.dao.UserMapper;
import me.ianhe.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iHelin
 * @since 2017/8/29 17:36
 */
@Service
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        System.out.println("username is " + username);
        User user = userMapper.selectByUsername(username);
        if (user == null) throw new UsernameNotFoundException("user not found");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

//        for (UserProfile userProfile : user.getUserProfiles()) {
//            System.out.println("UserProfile : " + userProfile);
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }
        System.out.println("authorities :" + authorities);
        return authorities;
    }
}
