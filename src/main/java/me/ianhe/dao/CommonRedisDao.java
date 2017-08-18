package me.ianhe.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * CommonRedisDao
 *
 * @author iHelin
 * @create 2017-04-11 15:07
 */
@Repository
public class CommonRedisDao {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void set(String key, String val) {
        stringRedisTemplate.opsForValue().set(key, val);
    }

    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     * 取得Integer值
     *
     * @param key
     * @return
     */
    public Integer getInt(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isNotBlank(value)) {
            return Integer.valueOf(value);
        }
        return 0;
    }

    public Long getLong(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isNotBlank(value)) {
            return Long.valueOf(value);
        }
        return 0L;
    }

    /**
     * 获取
     *
     * @param key
     * @param retain 是否保留
     * @return
     */
    public String get(String key, boolean retain) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (!retain) {
            stringRedisTemplate.delete(key);
        }
        return value;
    }

    /**
     * 递增操作
     *
     * @param key
     * @param by
     * @return
     */
    public double incr(String key, double by) {
        return stringRedisTemplate.opsForValue().increment(key, by);
    }

    public Long incr(String key, long by) {
        return stringRedisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 递增1
     * key不存在时默认为0，返回1
     *
     * @param key
     * @return
     */
    public Long incr(String key) {
        return stringRedisTemplate.boundValueOps(key).increment(1L);
    }

    /**
     * 将map写入缓存
     *
     * @param key
     * @param map
     */
    public <T> void setMap(String key, Map<String, T> map) {
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param value 值
     */
    public void addMap(String key, String field, String value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 获取map缓存
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> Map<String, T> mget(String key, Class<T> clazz) {
        BoundHashOperations<String, String, T> boundHashOperations = stringRedisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }


    /**
     * 获取map缓存中的某个对象
     *
     * @param key
     * @param field
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field) {
        return (T) stringRedisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 删除map中的某个对象
     *
     * @param key   map对应的key
     * @param field map中该对象的key
     * @author lh
     * @date 2016年8月10日
     */
    public void delMapField(String key, String... field) {
        BoundHashOperations<String, String, ?> boundHashOperations = stringRedisTemplate.boundHashOps(key);
        boundHashOperations.delete(field);
    }

    /**
     * 指定缓存的失效时间
     *
     * @param key     缓存KEY
     * @param timeout 失效时间(秒)
     * @author FangJun
     * @date 2016年8月14日
     */
    public void setExpire(String key, long timeout) {
        if (timeout > 0) {
            stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 添加set
     *
     * @param key
     * @param values
     */
    public void addSet(String key, String... values) {
        stringRedisTemplate.boundSetOps(key).add(values);
    }

    /**
     * 删除set集合中的对象
     *
     * @param key
     * @param value
     */
    public void delSet(String key, String... value) {
        stringRedisTemplate.boundSetOps(key).remove(value);
    }

    /**
     * set重命名
     *
     * @param oldKey
     * @param newKey
     */
    public void renameSet(String oldKey, String newKey) {
        stringRedisTemplate.boundSetOps(oldKey).rename(newKey);
    }

    /**
     * key是否存在
     */
    public boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 设置值和过期时间
     *
     * @author iHelin
     * @since 2017/8/17 16:32
     */
    public void setTimeout(String key, String value, Long timeout,
                           TimeUnit unit) {
        if (value == null) {
            return;
        }
        if (timeout != null) {
            stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
        } else {
            stringRedisTemplate.opsForValue().set(key, value);
        }
    }

}
