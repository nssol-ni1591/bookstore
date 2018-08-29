package bookstore.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {

	private DB() {
		// Do nothing
	}

	/*
	 */
	public static Connection createConnection() throws ClassNotFoundException, SQLException, IOException {
		final Logger log = Logger.getLogger(DB.class.getName());

		Properties props = new Properties();
		InputStream is = DB.class.getResourceAsStream("/META-INF/jdbc.properties");
		if (is != null) {
			props.load(new InputStreamReader(is));
		}

		String username = props.getProperty("username");
		String password = props.getProperty("password");

		String env = System.getProperty("jdbc.env", "dev");
		String driver = props.getProperty(env + ".driver");
		String url = props.getProperty(env + ".url");

		log.log(Level.FINE, "jdbc.env={0}, url={1}, driver={2}", new Object[] { env, url, driver });

		if (driver != null && !driver.isEmpty()) {
			// derby, ojdbc8�̏ꍇ��JDBC4.0�ɑΉ����Ă���̂�Class.forName()�͕K�v�Ȃ�
			// ojdbc14�́AJDBC4.0�ɑΉ����Ă��Ȃ�����Class.forName()���K�v
			Class.forName(driver);
		}

		return DriverManager.getConnection(url, username, password);
	}

}