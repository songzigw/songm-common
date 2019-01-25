package cn.songm.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.connection.RedisConnection;

public interface BaseRedis<T> {

    /**
     * 序列化
     * 
     * @param entity
     * @param connection
     * @return
     */
    public T serialize(T entity, RedisConnection connection);
    
    /**
     * 反序列化
     * 
     * @param entity
     * @param connection
     * @return
     */
    public T unserialize(T entity, RedisConnection connection);
    
    /**
     * 指定缓存失效时间
     * 
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time);

    /**
     * 获取缓存过期时间
     * 
     * @param key
     *            键不能为null
     * @return 秒，0代表永久有效
     */
    public long getExpire(String key);

    /**
     * 判断key是否存在
     * 
     * @param key
     * @return
     */
    public boolean hasKey(String key);

    /**
     * 删除缓存
     * 
     * @param key
     */
    public void del(String... key);

    // ============String===============

    /**
     * 获取缓存
     * 
     * @param key
     * @return
     */
    public Object get(String key);

    /**
     * 放入缓存
     * 
     * @param key
     * @param value
     * @return
     */
    public void set(String key, Object value);

    /**
     * 放入缓存并设置时间
     * 
     * @param key
     * @param value
     * @param time
     */
    public void set(String key, Object value, long time);
    
    /**
     * 递增
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta);
    
    /**
     * 递减
     * @param key
     * @param delta
     * @return
     */
    public long decr(String key, long delta);
    
    // =================Map====================
    
    /**
     * HashGet
     * 
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key, String item);

    /**
     * 获取HashKey对应的所有键值
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key);
    
    /**
     * HashSet
     * @param key
     * @param map
     */
    public void hmset(String key, Map<String, Object> map);
    
    /**
     * HashSet 并设置失效时间
     * @param key
     * @param map
     * @param time
     */
    public void hmset(String key, Map<String, Object> map, long time);

    /**
     * 向一张hash表中放入数据，如果不存在将创建
     * @param key
     * @param item
     * @param value
     */
    public void hset(String key, String item, Object value);

    /**
     * 向一张hash表中放入数据，如果不存在将创建
     * @param key
     * @param item
     * @param value
     * @param time 时间（秒）
     */
    public void hset(String key, String item, Object value, long time);
    
    /**
     * 删除hash表中的值
     * @param key
     * @param item
     */
    public void hdel(String key, Object... item);

    /**
     * 判断hash表中是否有该项的值
     * @param key
     * @param item
     * @return
     */
    public boolean hHasItem(String key, String item);
    
    /**
     * Hash递增
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hincr(String key, String item, double by);
    
    /**
     * Hash递减
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hdecr(String key, String item, double by);
    
    // ==============Set===============
    
    /**
     * 根据key获取set中的所有值
     * @param key
     * @return
     */
    public Set<Object> sget(String key);
    
    /**
     * 查询set是否存在这个元素
     * @param key
     * @param value
     * @return
     */
    public boolean sHasValue(String key, Object value);
    
    /**
     * 将数据放入set缓存
     * @param key
     * @param values
     * @return
     */
    public long sset(String key, Object... values);
    
    /**
     * 将数据放入set缓存
     * @param key
     * @param time
     * @param values
     * @return
     */
    public long sset(String key, long time, Object... values);
    
    /**
     * 获取set缓存的长度
     * @param key
     * @return
     */
    public long sGetSize(String key);
    
    /**
     * 移除值为value的元素
     * @param key
     * @param values
     * @return
     */
    public long setRemove(String key, Object... values);
    
    // ================List==================
    
    /**
     * 获取List缓存的内容
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lGet(String key, long start, long end);
    
    public long lGetSize(String key);
    
    public Object lGetIndex(String key, long index);
    
    public Long lSet(String key, Object value);
    
    public Long lSet(String key, Object value, long time);
    
    public Long lSet(String key, List<Object> values);
    
    public Long lSet(String key, List<Object> values, long time);
    
    public void lUpdateIndex(String key, long index, Object value);
    
    public long lRemove(String key, long count, Object value);
}
