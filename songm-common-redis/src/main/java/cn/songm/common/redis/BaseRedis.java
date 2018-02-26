package cn.songm.common.redis;

import java.util.Set;

import org.springframework.data.redis.connection.RedisConnection;

public interface BaseRedis<T> {

    /**
     * 通过正则匹配keys
     * @param pattern
     * @return
     */
    public Set<String> getkeys(String pattern);
    
    /**
     * 检查key是否已经存在
     * 
     * @param key
     * @return
     */
    public boolean exists(String key);

    /**
     * 通过key删除
     * 
     * @param key
     */
    public long del(String... keys);
    
    /**
     * 为给定 key 设置过期时间
     * @param keys
     * @param seconds
     */
    public void expire(String key, long seconds);
    
    /**
     * 清空所有数据
     * 
     * @return
     */
    public String flushDB();

    /**
     * 查看有多少数据库
     */
    public long dbSize();

    /**
     * 检查是否连接成功
     * 
     * @return
     */
    public String ping();
    
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
    
    // String类型

    /**
     * 添加键值对并且设置存活时间
     * 
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加键值对并且设置存活时间
     * 
     * @param key
     * @param value
     * @param liveTime
     *            单位秒
     */
    public void set(String key, String value, long liveTime);

    /**
     * 添加键值对
     * 
     * @param key
     * @param value
     */
    public void set(String key, String value);

    /**
     * 添加键值对
     * 
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value);

    /**
     * 获取值
     * 
     * @param key
     * @return
     */
    public String get(String key);

    // List 类型
    
}
