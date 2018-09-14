package bookstore.dao;

import bookstore.pbean.TCustomer;

public interface CustomerDAO {

	public int getCustomerNumberByUid(String inUid);

	public TCustomer findCustomerByUid(String inUid);

	public void saveCustomer(String inUid,
			String inPasswordMD5,
			String inName,
			String inEmail);

}
