package bookstore.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Messages extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	private static final Properties props = new Properties();
	
	static {
		try {
			InputStream is = Messages.class.getResourceAsStream("/META-INF/MessageResources.properties");
			if (is != null) {
				props.load(new InputStreamReader(is));
			}
		}
		catch (IOException e) {
			Logger.getLogger(Messages.class.getName()).log(Level.SEVERE, "", e);
		}
	}

	public static String getMessage(String name) {
		return props.getProperty(name, name);
	}
	
	public void add(String key, String name) {
		put(key, getMessage(name));
	}
}
