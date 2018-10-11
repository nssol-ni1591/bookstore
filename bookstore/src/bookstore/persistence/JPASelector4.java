package bookstore.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import bookstore.jsf.bean.CommonJSFBean;

@Named
@Dependent
public class JPASelector4 {

	//JTAでは@PersistenceContextを使用する
	//RESOURCE_LOCALでは@PersistenceUnitを使用する

	@PersistenceContext(unitName = "BookStore2") private EntityManager defaultEM;
	@PersistenceContext(unitName = "BookStore-Hibernate-JTA") private EntityManager hibernateEM;
	@PersistenceContext(unitName = "BookStore-Eclipselink-JTA") private EntityManager eclipselinkEM;
	@PersistenceContext(unitName = "BookStore-OpenJPA-JTA") private EntityManager openjpaEM;

	@PersistenceUnit(name = "BookStore") private EntityManagerFactory defaultEMF;
	@PersistenceUnit(name = "BookStore-Hibernate-Resource_Local") private EntityManagerFactory hibernateEMF;
	@PersistenceUnit(name = "BookStore-Eclipselink-Resource_Local") private EntityManagerFactory eclipselinkEMF;
	@PersistenceUnit(name = "BookStore-OpenJPA-Resource_Local") private EntityManagerFactory openjpaEMF;

	public static final String DEFAULT = "Default";
	public static final String HIBERNATE = "Hibernate";
	public static final String ECLIPSELINK = "Eclipselink";
	public static final String OPENJPA = "OpenJPA";

	public static final String JTA = "JTA";
	public static final String RESOURCE_LOCAL = "Resource_Local";

	@Inject private CommonJSFBean common;
	@Inject private Logger log;

	private String persistenceName(String jpaModule, String txType) throws NamingException {
		if (jpaModule == null) {
			jpaModule = DEFAULT;
		}

		switch (jpaModule) {
		case DEFAULT:
		case HIBERNATE:
		case ECLIPSELINK:
		case OPENJPA:
			break;
		default:
			throw new NamingException("unknown jpa module: " + jpaModule);
		}

		if (!JTA.equals(txType) && !RESOURCE_LOCAL.equals(txType)) {
			throw new NamingException("unknown transaction type: " + txType);
		}

		String name = String.join("-", "BookStore", jpaModule, txType);
		log.log(Level.INFO, "persistence={0}", new Object[] { name });
		return name;
	}

	//@Produces
	public EntityManager getEntityManager() throws NamingException {
		return getEntityManager(common.getJpaModule(), common.getTxType());
	}
	public EntityManager getEntityManager(String txType) throws NamingException {
		return getEntityManager(null, txType);
	}
	public EntityManager getEntityManager(String jpaModule, String txType) throws NamingException {
		String name = persistenceName(jpaModule, txType);

		InitialContext iContext = new InitialContext();
		Context context = (Context) iContext.lookup("java:comp/env");
		EntityManager em = (EntityManager) context.lookup(name);
		log.log(Level.INFO, "entityManager={0}", new Object[] { em });
		return em;
	}

	public void closeEntityManager(/*@Disposes*/ EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

}
