package bookstore.service;

import java.sql.SQLException;

import bookstore.vbean.VCustomer;

public interface CustomerService {

	public boolean isAlreadyExsited(String inSid) throws SQLException;

	public boolean createCustomer(String inUid, String inPassword, String inName, String inEmail) throws Exception;

	public boolean isPasswordMatched(String inUid, String inPassword) throws SQLException;

	public VCustomer createVCustomer(String inUid) throws SQLException;
}
