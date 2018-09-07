package bookstore.logic.impl;

import bookstore.dao.CustomerDAO;
import bookstore.logic.CustomerLogic;
import bookstore.pbean.TCustomer;
import bookstore.vbean.VCustomer;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class CustomerLogicImpl implements CustomerLogic {

	private CustomerDAO customerdao;


	public boolean isAlreadyExsited(String inUid) {
		int count = customerdao.getCustomerNumberByUid(inUid);
		getLogger().log(Level.INFO, "count={0}", count);
		return count != 0;
	}

	public boolean createCustomer(String inUid, String inPassword, String inName, String inEmail) {

		if (isAlreadyExsited(inUid)) {
			return false;
		}

		String passwordMD5 = getStringDigest(inPassword);

		customerdao.saveCustomer(inUid, passwordMD5, inName, inEmail);

		return true;
	}

	public boolean isPasswordMatched(String inUid, String inPassword) {

		if (!isAlreadyExsited(inUid)) {
			return false;
		}

		TCustomer customer = customerdao.findCustomerByUid(inUid);
		getLogger().log(Level.INFO, "customer={0}", customer);
		return customer.getPasswordmd5().equals(getStringDigest(inPassword));
	}

	public VCustomer createVCustomer(String inUid) {
		return new VCustomer(customerdao.findCustomerByUid(inUid));
	}

	private static String getStringDigest(String inString) {
		return DigestUtils.md5Hex(inString + "digested");
	}

	protected void setCustomerdao(CustomerDAO inCdao) {
		this.customerdao = inCdao;
	}

	protected abstract Logger getLogger();

}