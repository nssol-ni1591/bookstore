package bookstore.logic.impl;

import bookstore.dao.CustomerDAO;
import bookstore.logic.CustomerLogic;
import bookstore.pbean.TCustomer;
import bookstore.vbean.VCustomer;

import org.apache.commons.codec.digest.DigestUtils;

public class CustomerLogicImpl implements CustomerLogic {

	private CustomerDAO customerdao;

	public CustomerLogicImpl() {
		System.out.println("impl.CustomerLogicImpl<init>:");
	}

	public boolean isAlreadyExsited(String inUid) {
		int count = customerdao.getCustomerNumberByUid(inUid);
		System.out.println("CustomerLogicImpl.isAlreadyExsited: count=" + count);
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
		System.out.println("CustomerLogicImpl.isPasswordMatched: customer=" + customer);
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

}