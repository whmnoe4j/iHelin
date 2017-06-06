package me.ianhe.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * AbstractBaseRedisDao
 *
 * @author iHelin
 * @create 2017-04-11 15:07
 */
@Repository
public class CommonRedisDao implements RedisDao {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Override
    public void saveMap(String key, Map<String, String> value) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);
        try {
            ops.putAll(value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, String> getMap(String key) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        return ops.entries(key);
    }

    @Override
    public void delHashKeyField(String key, String field) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(key);
        ops.delete(field);
    }

    @Override
    public void delKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void saveString(String key, String value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    @Override
    public String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void saveExpireString(String key, String value, long liveTime) {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.set(key.getBytes(), value.getBytes());
            if (liveTime > 0) {
                connection.expire(key.getBytes(), liveTime);
            }
            return 1L;
        });
    }

    @Override
    public void saveSet(String key, Set<String> value) {
        BoundSetOperations<String, String> ops = redisTemplate.boundSetOps(key);
        try {
            ops.add(value.toArray(new String[value.size()]));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void addSetValue(String key, String value) {
        BoundSetOperations<String, String> ops = redisTemplate.boundSetOps(key);
        ops.add(value);
    }

    @Override
    public Set<String> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public void saveList(String key, List<String> value) {
        ListOperations<String, String> ops = redisTemplate.opsForList();
        try {
            ops.rightPushAll(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getList(String key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public void addListValue(String key, String value) {
        ListOperations<String, String> ops = redisTemplate.opsForList();
        ops.rightPush(key, value);
    }

    @Override
    public String getListPop(String key) {
        ListOperations<String, String> ops = redisTemplate.opsForList();
        return ops.leftPop(key);
    }

    @Override
    public void saveZSet(String key, String value, Integer size) {
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        ops.add(key, value, System.currentTimeMillis());
        Integer nSize = -1 - size;
        ops.removeRange(key, 0, nSize);
    }

    @Override
    public void saveZSet(String key, String value, double score) {
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        ops.add(key, value, score);
    }

    @Override
    public Set<String> getZSet(String key, int start, int end) {
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        return ops.reverseRange(key, start, end);
    }

    @Override
    public void removeZSet(String key, String value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

    @Override
    public void removeZSet(String key, Set<String> set) {
        redisTemplate.opsForZSet().remove(key, set.toArray(new String[set.size()]));
    }

    @Override
    public Set<String> rangeByScore(String key, double min, double max) {
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        return ops.rangeByScore(key, min, max);
    }

    @Override
    public Set<String> rangeByScore(String key, double min, double max, long offset, long count) {
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        return ops.rangeByScore(key, min, max, offset, count);
    }

    @Override
    public void incrementZSetScore(String key, String value, double score) {
        ZSetOperations<String, String> ops = redisTemplate.opsForZSet();
        ops.incrementScore(key, value, score);
    }

}
