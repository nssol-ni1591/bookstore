package bookstore.logic;

import bookstore.vbean.VCustomer;

public interface CustomerLogic {
	public boolean isAlreadyExsited( String inSid );
	public boolean createCustomer( String inUid,
									 String inPassword,
									 String inName,
									 String inEmail );
	public boolean isPasswordMatched( String inUid,
										String inPassword );
	public VCustomer createVCustomer( String inUid );
}
