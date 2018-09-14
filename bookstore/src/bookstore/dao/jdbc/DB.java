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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DB {

	private static final Logger log = Logger.getLogger(DB.class.getName());

	private DB() {
		// Do nothing
	}

	/*
	 * �R�l�N�V�����������ŊǗ�
	 */
	public static Connection createConnection2() throws ClassNotFoundException, SQLException, IOException {
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
			// derby, ojdbc8�̏ꍇ��JDBC4.0�ɑΉ����Ă���̂�Class.forName()�͕K�v�Ȃ�
			// ojdbc14�́AJDBC4.0�ɑΉ����Ă��Ȃ�����Class.forName()���K�v
			Class.forName(driver);
		}

		return DriverManager.getConnection(url, username, password);
	}
	/*
	 * �R�l�N�V�����v�[��
	 */
	public static Connection createConnection() throws ClassNotFoundException, SQLException, IOException, NamingException {
		Context context = new InitialContext();
		DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/bookstoreDS");
		Connection con = ds.getConnection();
		return con;
	}

	public static void close(String className, ResultSet rs, PreparedStatement pst, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}
		if (pst != null) {
			try {
				pst.close();
			}
			catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}
		if (con != null) {
			try {
				con.close();
			}
			catch (SQLException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		}

	}

}
