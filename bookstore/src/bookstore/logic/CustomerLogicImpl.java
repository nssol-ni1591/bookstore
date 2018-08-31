package bookstore.logic;

import bookstore.dao.CustomerDAO;
import bookstore.pbean.TCustomer;
import bookstore.vbean.VCustomer;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerLogicImpl implements CustomerLogic {

	@Autowired CustomerDAO customerdao;

	public boolean isAlreadyExsited(String inUid) {
		return customerdao.getCustomerNumberByUid(inUid) != 0;
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
		return customer.getPasswordmd5().equals(getStringDigest(inPassword));
	}

	public VCustomer createVCustomer(String inUid) {
		return new VCustomer(customerdao.findCustomerByUid(inUid));
	}

	private static String getStringDigest(String inString) {
		return DigestUtils.md5Hex(inString + "digested");
	}
/*
	public void setCustomerdao(CustomerDAO inCdao) {
		this.customerdao = inCdao;
	}
*/
}