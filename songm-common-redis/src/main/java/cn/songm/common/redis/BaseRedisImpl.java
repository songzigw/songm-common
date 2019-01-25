package cn.songm.common.redis;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class BaseRedisImpl<T> implements BaseRedis<T> {

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean expire(String key, long time) {
        if (time <= 0) {
            return false;
        }
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void del(String... key) {
        if (key == null || key.length == 0) {
            return;
        }
        if (key.length == 1) {
            redisTemplate.delete(key[0]);
            return;
        }
        redisTemplate.delete(Arrays.asList(key));
    }

    // ============String===============

    @Override
    public Object get(String key) {
        return key == null ? key : redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }
    
    @Override
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("delta: " + delta + ", it bas to be greater than zero.");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }
    
    @Override
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("delta: " + delta + ", it bas to be greater than zero.");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    
    // =================Map====================
    
    @Override
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    
    @Override
    public void hmset(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }
    
    @Override
    public void hmset(String key, Map<String, Object> map, long time) {
        hmset(key, map);
        if (time > 0) {
            expire(key, time);
        }
    }

    @Override
    public void hset(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    @Override
    public void hset(String key, String item, Object value, long time) {
        hset(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
    }
    
    @Override
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    @Override
    public boolean hHasItem(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }
    
    @Override
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }
    
    @Override
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    
    // ==============Set===============
    
    @Override
    public Set<Object> sget(String key) {
        return redisTemplate.opsForSet().members(key);
    }
    
    @Override
    public boolean sHasValue(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }
    
    @Override
    public long sset(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }
    
    @Override
    public long sset(String key, long time, Object... values) {
        long c = sset(key, values);
        if (time > 0) expire(key, time);
        return c;
    }
    
    @Override
    public long sGetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }
    
    @Override
    public long setRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }
    
    // ================List==================
    
    @Override
    public List<Object> lGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }
    
    @Override
    public long lGetSize(String key) {
        return redisTemplate.opsForList().size(key);
    }
    
    @Override
    public Object lGetIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }
    
    @Override
    public Long lSet(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }
    
    @Override
    public Long lSet(String key, Object value, long time) {
        Long l = lSet(key, value);
        if (time > 0) expire(key, time);
        return l;
    }
    
    @Override
    public Long lSet(String key, List<Object> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }
    
    @Override
    public Long lSet(String key, List<Object> values, long time) {
        Long l = lSet(key, values);
        if (time > 0) expire(key, time);
        return l;
    }
    
    @Override
    public void lUpdateIndex(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }
    
    @Override
    public long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

}
