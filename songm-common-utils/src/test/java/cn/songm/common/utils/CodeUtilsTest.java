package cn.songm.common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CodeUtilsTest {

	private static char[] mark = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '张', '松',
			'好', '的', '很', '太', '棒', '了', '天', '地', '人', '宇', '宙', '大', '小', '玩', '叶', '舟', '黄', '丹' };

	public static String getSequence() {
		int l = 9;
		StringBuilder sb = new StringBuilder(l);
		for (int i = 0; i < l; i++) {
			sb.append(mark[(int) (Math.random() * mark.length)]);
		}
		return sb.toString();
	}

	@Test
	public void testSha1() {
		String s = "abc";
		String a = "a9993e364706816aba3e25717850c26c9cd0d89d";
		Assert.assertEquals(a, CodeUtils.sha1(s));
	}

	@Test
	public void testBytesAndHex() {
		String a = getSequence();
		String hex = CodeUtils.bytes2hex(a.getBytes());
		Assert.assertEquals(a, new String(CodeUtils.hex2bytes(hex)));
	}

}
