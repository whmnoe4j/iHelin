package me.ianhe.service;

import com.google.common.collect.Maps;
import me.ianhe.dao.UserMapper;
import me.ianhe.db.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/8/29 17:36
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    public int updateUser(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    public int deleteUserById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public User selectUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User selectUserByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }

    public User selectUserByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    public List<User> listUserByCondition(String nickName, Integer status, int offset, int size) {
        Map<String, Object> res = Maps.newHashMap();
        if (StringUtils.isNotEmpty(nickName)) {
            res.put("nickName", nickName);
        }
        if (status != null) {
            res.put("status", status);
        }
        return userMapper.listUserByCondition(res, new RowBounds(offset, size));
    }

    public int listUserCount(String nickName, Integer status) {
        Map<String, Object> res = Maps.newHashMap();
        if (StringUtils.isNotEmpty(nickName)) {
            res.put("nickName", nickName);
        }
        if (status != null) {
            res.put("status", status);
        }
        return userMapper.listUserCount(res);
    }

}
