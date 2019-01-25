package cn.songm.common.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.songm.common.entity.Tab01;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-common-mybatis.xml" })
@Transactional
public class Tab01DaoTest {

	@Autowired
	private Tab01Dao tab01Dao;
	
	@Test
	@Rollback(true)
	public void testInsertAndSelectOneById() {
		Tab01 tab01 = new Tab01();
		tab01.init();
		tab01.setCount(0);
		int n = tab01Dao.insert(tab01);
		Assert.assertSame("插入数据返回值不为" + n, 1, n);
		
		Tab01 tab = tab01Dao.selectOneById(tab01.getNo());
		Assert.assertNotNull("数据查询不到", tab);
		Assert.assertEquals("查询结果和预期结果不完全一致", tab.toString(), tab01.toString());
	}
	
	@Test
	public void testIncrOne() {
		Tab01 tab01 = new Tab01();
		tab01.init();
		tab01.setCount(0);
		tab01Dao.insert(tab01);
		tab01Dao.incr(tab01.getNo());
		
		Tab01 tab = tab01Dao.selectOneById(tab01.getNo());
		Assert.assertSame("递增1次", tab.getCount(), 1);
	}
	
	@Test
	public void testIncrTwo() {
		Tab01 tab01 = new Tab01();
		tab01.init();
		tab01.setCount(0);
		tab01Dao.insert(tab01);
		tab01Dao.incr(tab01.getNo());
		tab01Dao.incr(tab01.getNo());
		
		Tab01 tab = tab01Dao.selectOneById(tab01.getNo());
		int expected = tab.getCount();
		int actual = 2;
		
		Assert.assertSame(
				String.format("递增两次，运行值%d，预期值%d",
				expected, actual), expected, actual);
	}
}
