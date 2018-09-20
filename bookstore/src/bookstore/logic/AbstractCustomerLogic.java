package bookstore.logic;

import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;
import bookstore.vbean.VCustomer;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class AbstractCustomerLogic implements CustomerLogic {

	protected abstract CustomerDAO getCustomerDAO();
	protected abstract Logger getLogger();

	public boolean isAlreadyExsited(String inUid) throws SQLException {
		CustomerDAO customerdao = getCustomerDAO();
		int count = customerdao.getCustomerNumberByUid(inUid);
		getLogger().log(Level.INFO, "count={0}", count);
		return count != 0;
	}

	public boolean createCustomer(String inUid, String inPassword, String inName, String inEmail) throws SQLException {
		if (isAlreadyExsited(inUid)) {
			return false;
		}

		String passwordMD5 = getStringDigest(inPassword);
		CustomerDAO customerdao = getCustomerDAO();
		customerdao.saveCustomer(inUid, passwordMD5, inName, inEmail);
		return true;
	}

	public boolean isPasswordMatched(String inUid, String inPassword) throws SQLException {
		if (!isAlreadyExsited(inUid)) {
			return false;
		}

		CustomerDAO customerdao = getCustomerDAO();
		TCustomer customer = customerdao.findCustomerByUid(inUid);
		getLogger().log(Level.INFO, "customer={0}", customer);
		return customer.getPasswordmd5().equals(getStringDigest(inPassword));
	}

	public VCustomer createVCustomer(String inUid) throws SQLException {
		CustomerDAO customerdao = getCustomerDAO();
		return new VCustomer(customerdao.findCustomerByUid(inUid));
	}

	private static String getStringDigest(String inString) {
		return DigestUtils.md5Hex(inString + "digested");
	}

}