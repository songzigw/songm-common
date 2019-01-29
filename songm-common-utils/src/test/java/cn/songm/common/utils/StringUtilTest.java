package cn.songm.common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StringUtilTest {

	@Test
	public void testToString() throws IllegalArgumentException, IllegalAccessException {
		ObjTest obj1 = new ObjTest();
		obj1.setA("aaa");
		obj1.setB("bbb");
		obj1.setC("ccc");
		String t1 = StringUtils.toString(obj1);
		
		ObjTest obj2 = new ObjTest();
		obj2.setA("aaa");
		obj2.setB("bbb");
		obj2.setC("ccc");
		String t2 = StringUtils.toString(obj2);
		
		Assert.assertEquals("对象不相等", t1, t2);
	}

	@Test
	public void testParseBooleanTRUE() {
		// 输入值
		String param = "true";
		// 预期值
		Boolean expected = true;
		// 实际值
		Boolean actual = StringUtils.parseBoolean(param);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testParseBooleanFalse() {
		// 输入值
		String param = "false";
		// 预期值
		Boolean expected = false;
		// 实际值
		Boolean actual = StringUtils.parseBoolean(param);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testParseBooleanNull() {
		// 输入值
		String param = "";
		// 预期值
		Boolean expected = null;
		// 实际值
		Boolean actual = StringUtils.parseBoolean(param);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testParseBooleanException() {
		// 输入值
		String param = "abc";
		try {
			StringUtils.parseBoolean(param);
			Assert.fail("没有出现异常");
		} catch (IllegalArgumentException e) {}
	}
	
}
