package bookstore.util;

import javax.persistence.EntityManager;

public interface EntityTx {

	void startEntityTx();
	void stopEntityTx();
	EntityManager getManager();

}
