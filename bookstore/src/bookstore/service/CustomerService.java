package bookstore.service;

import java.sql.SQLException;

import bookstore.vbean.VCustomer;

public interface CustomerService {

	public boolean isAlreadyExsited(String inSid) throws SQLException;

	public boolean createCustomer(String uid, String password, String name, String email) throws SQLException;

	public boolean isPasswordMatched(String uid, String password) throws SQLException;

	public VCustomer createVCustomer(String uid) throws SQLException;
}
