package bookstore.dao;

import java.sql.SQLException;

import bookstore.pbean.TCustomer;

public interface CustomerDAO {

	public int getCustomerNumberByUid(String inUid) throws SQLException;

	public TCustomer findCustomerByUid(String inUid) throws SQLException;

	public void saveCustomer(String inUid,
			String inPasswordMD5,
			String inName,
			String inEmail) throws SQLException;

}
