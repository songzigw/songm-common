package cn.songm.common.hadoop;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-common-hadoop.xml" })
public class HDFSUtilsTest {

	@Autowired
	private FileSystem fileSystem;

	@Before
	public void before() {
	}

	@After
	public void after() {
		try {
			fileSystem.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAppendFile() {
		HDFSUtils hdfs = new HDFSUtils(fileSystem);
		hdfs.appendFile("/words", "hhhhhh\n");
	}
}
