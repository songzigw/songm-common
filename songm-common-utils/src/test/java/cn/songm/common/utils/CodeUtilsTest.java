package cn.songm.common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CodeUtilsTest {

	@Test
    public void testSha1() {
		String s = "abc";
        String a = "d7e73e364706ff6ac63e25717850be6ce4b0a8e3";
        Assert.assertEquals(a, CodeUtils.sha1(s));
    }
	
}
