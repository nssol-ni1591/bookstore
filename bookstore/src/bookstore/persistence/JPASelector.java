package bookstore.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import bookstore.jsf.bean.CommonJSFBean;

@Named
@Dependent
public class JPASelector {

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

	@Produces
	public EntityManager getEntityManager() {
		return getEntityManager(common.getJpaModule(), common.getTxType());
	}
	public EntityManager getEntityManager(String txType) {
		return getEntityManager(null, txType);
	}
	public EntityManager getEntityManager(String jpaModule, String txType) {
		if (!JTA.equals(txType) && !RESOURCE_LOCAL.equals(txType)) {
			throw new IllegalArgumentException("unknown resource type: " + txType);
		}

		if (jpaModule == null) {
			jpaModule = DEFAULT;
		}

		EntityManager em;
		switch (jpaModule) {
		case DEFAULT:
			em = JTA.equals(txType) ? defaultEM : defaultEMF.createEntityManager();
			break;
		case HIBERNATE:
			em = JTA.equals(txType) ? hibernateEM : hibernateEMF.createEntityManager();
			break;
		case ECLIPSELINK:
			em = JTA.equals(txType) ? eclipselinkEM : eclipselinkEMF.createEntityManager();
			break;
		case OPENJPA:
			em = JTA.equals(txType) ? openjpaEM : openjpaEMF.createEntityManager();
			break;
		default:
			throw new IllegalArgumentException("unknown jpa module: " + jpaModule);
		}

		log.log(Level.INFO, "persistence={0}-{1}-{2}, entity={3}", new Object[] { "BookStore", jpaModule, txType, em });
		return em;
	}

	public void closeEntityManager(@Disposes EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}

}
