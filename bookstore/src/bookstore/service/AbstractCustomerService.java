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

	public boolean isAlreadyExsited(String uid) throws SQLException {
		T em = getManager();
		CustomerDAO<T> customerdao = getCustomerDAO();
		int count = customerdao.getCustomerNumberByUid(em, uid);
		return count != 0;
	}

	public boolean createCustomer(String uid, String password, String name, String email) throws SQLException {
		if (isAlreadyExsited(uid)) {
			return false;
		}

		T em = getManager();
		String passwordMD5 = getStringDigest(password);
		CustomerDAO<T> customerdao = getCustomerDAO();
		customerdao.saveCustomer(em, uid, passwordMD5, name, email);
		return true;
	}

	public boolean isPasswordMatched(String uid, String password) throws SQLException {
		if (!isAlreadyExsited(uid)) {
			return false;
		}

		T em = getManager();
		CustomerDAO<T> customerdao = getCustomerDAO();
		TCustomer customer = customerdao.findCustomerByUid(em, uid);
		return customer.getPasswordmd5().equals(getStringDigest(password));
	}

	public VCustomer createVCustomer(String uid) throws SQLException {
		T em = getManager();
		CustomerDAO<T> customerdao = getCustomerDAO();
		return new VCustomer(customerdao.findCustomerByUid(em, uid));
	}

	private static String getStringDigest(String inString) {
		return DigestUtils.md5Hex(inString + "digested");
	}

}