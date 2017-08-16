package me.ianhe.dao;

import me.ianhe.utils.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * AbstractBaseRedisDao
 *
 * @author iHelin
 * @create 2017-04-11 15:07
 */
@Repository
public class CommonRedisDao {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void set(String key, String val) {
        stringRedisTemplate.opsForValue().set(key,val);
    }

    public Collection<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void delete(Collection<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     * 取得缓存（int型）
     *
     * @param key
     * @return
     */
    public Integer getInt(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isNotBlank(value)) {
            return Integer.valueOf(value);
        }
        return null;
    }

    /**
     * 获取缓存<br>
     * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
     *
     * @param key
     * @param retain 是否保留
     * @return
     */
    public Object getObj(String key, boolean retain) {
        Object obj = stringRedisTemplate.boundValueOps(key).get();
        if (!retain) {
            stringRedisTemplate.delete(key);
        }
        return obj;
    }

    /**
     * 将value对象以JSON格式写入缓存
     *
     * @param key
     * @param value
     * @param timeout 失效时间(秒)
     */
    public void setJson(String key, Object value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJson(value));
        if (timeout > 0) {
            stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 更新key对象field的值
     *
     * @param key   缓存key
     * @param field 缓存对象field
     * @param value 缓存对象field值
     */
    public void setJsonField(String key, String field, String value) {
        Map<String, Object> map = JSON.parseMap(stringRedisTemplate.boundValueOps(key).get());
        map.put(field, value);
        stringRedisTemplate.opsForValue().set(key, JSON.toJson(map));
    }


    /**
     * 递减操作
     *
     * @param key
     * @param by
     * @return
     */
    public double decr(String key, double by) {
        return stringRedisTemplate.opsForValue().increment(key, -by);
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

    /**
     * 获取double类型值
     *
     * @param key
     * @return
     */
    public double getDouble(String key) {
        String value = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isNotBlank(value)) {
            return Double.valueOf(value);
        }
        return 0d;
    }

    /**
     * 设置double类型值
     *
     * @param key
     * @param value
     * @param timeout 失效时间(秒)
     */
    public void setDouble(String key, double value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if (timeout > 0) {
            stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置double类型值
     *
     * @param key
     * @param value
     * @param timeout 失效时间(秒)
     */
    public void setInt(String key, int value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
        if (timeout > 0) {
            stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 将map写入缓存
     *
     * @param key
     * @param map
     * @param timeout 失效时间(秒)
     */
    public <T> void setMap(String key, Map<String, T> map, long timeout) {
        stringRedisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key
     * @param map
     */
    public <T> void addMap(String key, Map<String, T> map) {
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
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param obj   对象
     */
    public <T> void addMap(String key, String field, T obj) {
        stringRedisTemplate.opsForHash().put(key, field, obj);
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
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapField(String key, String field, Class<T> clazz) {
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
    public void expire(String key, long timeout) {
        if (timeout > 0) {
            stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 添加set
     *
     * @param key
     * @param value
     */
    public void sadd(String key, String... value) {
        stringRedisTemplate.boundSetOps(key).add(value);
    }

    /**
     * 删除set集合中的对象
     *
     * @param key
     * @param value
     */
    public void srem(String key, String... value) {
        stringRedisTemplate.boundSetOps(key).remove(value);
    }

    /**
     * set重命名
     *
     * @param oldkey
     * @param newkey
     */
    public void srename(String oldkey, String newkey) {
        stringRedisTemplate.boundSetOps(oldkey).rename(newkey);
    }

    /**
     * key是否存在
     */
    public boolean exists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public void setTime(String key, String obj, Long timeout,
                        TimeUnit unit) {
        if (obj == null) {
            return;
        }

        if (timeout != null) {
            stringRedisTemplate.opsForValue().set(key, obj, timeout, unit);
        } else {
            stringRedisTemplate.opsForValue().set(key, obj);
        }
    }

}
