package bookstore.service;

import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;
import bookstore.vbean.VCustomer;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class AbstractCustomerService<T> implements CustomerService {

	protected abstract CustomerDAO<T> getCustomerDAO();
	protected abstract Logger getLogger();
	protected abstract T getManager();

	public boolean isAlreadyExsited(String inUid) throws SQLException {
		T em = getManager();
		CustomerDAO<T> customerdao = getCustomerDAO();
		int count = customerdao.getCustomerNumberByUid(em, inUid);
		return count != 0;
	}

	public boolean createCustomer(String inUid, String inPassword, String inName, String inEmail) throws Exception {
		if (isAlreadyExsited(inUid)) {
			return false;
		}

		T em = getManager();
		String passwordMD5 = getStringDigest(inPassword);
		CustomerDAO<T> customerdao = getCustomerDAO();
		customerdao.saveCustomer(em, inUid, passwordMD5, inName, inEmail);
		return true;
	}

	public boolean isPasswordMatched(String inUid, String inPassword) throws SQLException {
		if (!isAlreadyExsited(inUid)) {
			return false;
		}

		T em = getManager();
		CustomerDAO<T> customerdao = getCustomerDAO();
		TCustomer customer = customerdao.findCustomerByUid(em, inUid);
		return customer.getPasswordmd5().equals(getStringDigest(inPassword));
	}

	public VCustomer createVCustomer(String inUid) throws SQLException {
		T em = getManager();
		CustomerDAO<T> customerdao = getCustomerDAO();
		return new VCustomer(customerdao.findCustomerByUid(em, inUid));
	}

	private static String getStringDigest(String inString) {
		return DigestUtils.md5Hex(inString + "digested");
	}

}