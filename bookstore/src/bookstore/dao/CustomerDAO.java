package bookstore.dao;

import java.sql.SQLException;

import bookstore.pbean.TCustomer;

public interface CustomerDAO<T> {

	public int getCustomerNumberByUid(final T em, String uid) throws SQLException;

	public TCustomer findCustomerByUid(final T em, String uid) throws SQLException;

	public void saveCustomer(final T em, 
			String uid,
			String passwordMD5,
			String name,
			String email) throws SQLException;

}
