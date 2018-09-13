package bookstore.dao.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

		log.log(Level.INFO, "jdbc.env={0}, url={1}, driver={2}", new Object[] { env, url, driver });

		if (driver != null && !driver.isEmpty()) {
			// derby, ojdbc8の場合はJDBC4.0に対応しているのでClass.forName()は必要ない
			// ojdbc14は、JDBC4.0に対応していないためClass.forName()が必要
			Class.forName(driver);
		}

		return DriverManager.getConnection(url, username, password);
	}

	public static void close(String className, ResultSet rs, PreparedStatement pst, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				Logger.getLogger(className).log(Level.SEVERE, e.getMessage());
			}
		}
		if (pst != null) {
			try {
				pst.close();
			}
			catch (SQLException e) {
				Logger.getLogger(className).log(Level.SEVERE, e.getMessage());
			}
		}
		if (con != null) {
			try {
				con.close();
			}
			catch (SQLException e) {
				Logger.getLogger(className).log(Level.SEVERE, e.getMessage());
			}
		}

	}

}
