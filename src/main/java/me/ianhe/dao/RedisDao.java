package me.ianhe.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by iHelin on 17/4/11.
 */
public interface RedisDao {

    /**
     * 保存map类型到redis库中
     *
     * @param key
     * @param value
     */
    void saveMap(String key, Map<String, String> value);

    /**
     * 根据key获取map对象
     *
     * @param key
     * @return
     */
    Map<String, String> getMap(String key);

    /**
     * 删除指定Map中field
     *
     * @param key
     * @param field
     */
    void delHashKeyField(String key, String field);

    /**
     * 删除key
     * 支持正则匹配
     *
     * @param key
     */
    void delKey(String key);

    /**
     * 是否存在此key
     *
     * @param key
     * @return
     */
    boolean hasKey(String key);

    /**
     * 设置String
     *
     * @param key
     * @param value
     */
    void saveString(String key, String value);

    /**
     * 根据key获取string值
     *
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 保存string值，需要设置过期时间（单位为：秒，为负数时没有过期时间）
     *
     * @param key
     * @param value
     * @param liveTime
     */
    void saveExpireString(String key, String value, long liveTime);

    /**
     * 更新Long，没有则会新增
     *
     * @param key
     * @param value
     */
    @Deprecated
    void incrementLong(String key, Long value);

    /**
     * 保存Set<String>
     *
     * @param key
     * @param value
     */
    void saveSet(String key, Set<String> value);

    /**
     * 给Set类型增加一个值
     *
     * @param key
     * @param value
     */
    void addSetValue(String key, String value);

    /**
     * 从redis中获取Set
     *
     * @param key
     * @return
     */
    Set<String> getSet(String key);

    /**
     * 保存List<String> 类型集合到redis中
     *
     * @param key
     * @param value
     */
    void saveList(String key, List<String> value);

    /**
     * 从redis中获取List
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<String> getList(String key, int start, int end);

    /**
     * 保存值到list类型中
     *
     * @param key
     * @param value
     */
    void addListValue(String key, String value);

    /**
     * pop操作
     *
     * @param key
     * @return
     */
    String getListPop(String key);

    /**
     * 保存value到zset类型集合中
     *
     * @param key
     * @param value
     * @param size  存储大小
     */
    void saveZSet(String key, String value, Integer size);

    /**
     * 保存value到zset类型集合中
     *
     * @param key
     * @param value
     * @param score 分数
     */
    void saveZSet(String key, String value, double score);

    /**
     * 从zset类型集合获取集合中
     *
     * @param key
     * @param start 开始索引
     * @param end   结束索引
     */
    Set<String> getZSet(String key, int start, int end);

    /**
     * 从zset中删除指定元素
     *
     * @param key
     * @param value
     */
    void removeZSet(String key, String value);

    /**
     * 从zset中批量删除指定元素
     *
     * @param key
     * @param set
     */
    void removeZSet(String key, Set<String> set);

    /**
     * 获取mix ≤ score ≤ max 范围内的元素
     *
     * @param key
     * @param min score 最小值
     * @param max score 最大值
     * @return
     */
    Set<String> rangeByScore(String key, double min, double max);

    /**
     * 获取mix ≤ score ≤ max 范围内，offset位置开始count数量的元素
     *
     * @param key
     * @param min    score 最小值
     * @param max    score 最大值
     * @param offset 偏移量
     * @param count  数量
     * @return
     */
    Set<String> rangeByScore(String key, double min, double max, long offset, long count);

    /**
     * ZSet score值自增长
     * 如果没有值会自动新增一个
     *
     * @param key
     * @param value
     * @param score
     */
    void incrementZSetScore(String key, String value, double score);

}
