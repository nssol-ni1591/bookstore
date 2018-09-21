package bookstore.dao;

import java.sql.SQLException;

import bookstore.pbean.TCustomer;

public interface CustomerDAO<T> {

	public int getCustomerNumberByUid(final T em, String inUid) throws SQLException;

	public TCustomer findCustomerByUid(final T em, String inUid) throws SQLException;

	public void saveCustomer(final T em, 
			String inUid,
			String inPasswordMD5,
			String inName,
			String inEmail) throws SQLException;

}
