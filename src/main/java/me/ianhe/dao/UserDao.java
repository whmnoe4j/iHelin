package me.ianhe.dao;

import java.util.List;

/**
 * @author iHelin
 * @create 2017-04-11 15:09
 */
public interface UserDao {

    /**
     * 新增
     *
     * @param user
     * @return
     */
    boolean add(TUser user);

    /**
     * 批量新增 使用pipeline方式
     *
     * @param list
     * @return
     */
    boolean add(List<TUser> list);

    /**
     * 删除
     *
     * @param key
     */
    void delete(String key);

    /**
     * 删除多个
     *
     * @param keys
     */
    void delete(List<String> keys);

    /**
     * 修改
     *
     * @param user
     * @return
     */
    boolean update(TUser user);

    /**
     * 通过key获取
     *
     * @param keyId
     * @return
     */
    TUser get(String keyId);
}
