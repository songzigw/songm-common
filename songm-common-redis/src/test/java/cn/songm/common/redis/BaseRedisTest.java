package cn.songm.common.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-common-redis.xml" })
public class BaseRedisTest extends BaseRedisImpl<Object> {

	@Override
	public Object serialize(Object entity, RedisConnection connection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object unserialize(Object entity, RedisConnection connection) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Test
	public void testSet() {
		String key = "username";
		String val = "zhangsong";
		this.set(key, val);
		Assert.assertEquals("set方法失败", get(key), val);
	}
}
