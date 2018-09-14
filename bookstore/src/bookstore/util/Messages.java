package bookstore.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

public class Messages extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(Messages.class.getName());
	private static final Properties props = new Properties();
	
	private transient HttpServletRequest request;
	
	static {
		try {
			InputStream is = Messages.class.getResourceAsStream("/META-INF/MessageResources.properties");
			if (is != null) {
				props.load(new InputStreamReader(is));
			}
		}
		catch (IOException e) {
			log.log(Level.SEVERE, "", e);
		}
	}

	public Messages(HttpServletRequest request) {
		this.request = request;
	}
	public static String getMessage(String name) {
		return props.getProperty(name, name);
	}
	
	public void add(String key, String name) {
		put(key, getMessage(name));
		request.setAttribute("errors", this);
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
