package bookstore.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Message {

	private static final Properties props = new Properties();
	
	static {
		try {
			InputStream is = Message.class.getResourceAsStream("/META-INF/MessageResources.properties");
			if (is != null) {
				props.load(new InputStreamReader(is));
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static String getMessage(String name) {
		return props.getProperty(name, name);
	}
}
