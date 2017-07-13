package cn.songm.common.redis;

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
     * 通过key删除
     * 
     * @param key
     */
    public long del(String... keys);

    /**
     * 添加key value并且设置存活时间(byte)
     * 
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加key value并且设置存活时间
     * 
     * @param key
     * @param value
     * @param liveTime
     *            单位秒
     */
    public void set(String key, String value, long liveTime);

    /**
     * 添加key value
     * 
     * @param key
     * @param value
     */
    public void set(String key, String value);

    /**
     * 添加key value (字节)(序列化)
     * 
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value);

    /**
     * 获取redis value (String)
     * 
     * @param key
     * @return
     */
    public String get(String key);

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
    public abstract boolean exists(String key);

    /**
     * 清空redis所有数据
     * 
     * @return
     */
    public abstract String flushDB();

    /**
     * 查看redis里有多少数据库
     */
    public abstract long dbSize();

    /**
     * 检查是否连接成功
     * 
     * @return
     */
    public abstract String ping();
}
