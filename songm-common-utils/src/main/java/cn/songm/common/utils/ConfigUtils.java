package cn.songm.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

	public static Properties loadProps(String configName) throws IOException {
		InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream(configName);
		Properties props = new Properties();
		props.load(input);
		input.close();
		return props;
	}

	public static Object getItem(String key, Properties props) {
		return props.getProperty(key);
	}
}
