package bookstore.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

public class JPASelector2 {

	//JTAでは@PersistenceContextを使用する
	//RESOURCE_LOCALでは@PersistenceUnitを使用する

	public static final String DEFAULT = "Default";
	public static final String HIBERNATE = "Hibernate";
	public static final String ECLIPSELINK = "Eclipselink";
	public static final String OPENJPA = "OpenJPA";

	public static final String JTA = "JTA";
	public static final String RESOURCE_LOCAL = "Resource_Local";

	@Inject private Logger log;

	public static EntityManager getEntityManager(String jpaType) {
		return getEntityManager(null, jpaType);
	}
	public static EntityManager getEntityManager(String jpaModule, String jpaType) {
		if (!JTA.equals(jpaType) && !RESOURCE_LOCAL.equals(jpaType)) {
			throw new IllegalArgumentException(jpaType + " is unknown type");
		}
		if (jpaModule == null) {
			return new JPASelector2().defaultJPA(jpaType);
		}

		EntityManager em;
		switch (jpaModule) {
		case HIBERNATE:
			em = new JPASelector2().hibernate(jpaType);
			break;
		case ECLIPSELINK:
			em = new JPASelector2().eclipselink(jpaType);
			break;
		case OPENJPA:
			em = new JPASelector2().openjpa(jpaType);
			break;
		default:
			throw new IllegalArgumentException(jpaModule + " is unknown module");
		}
		Logger.getLogger(JPASelector2.class.getName())
			.log(Level.INFO, "persistence={0}-{1}-{2}, entity={3}", new Object[] { "BookStore", jpaModule, jpaType, em });
		return em;
	}

	private EntityManager defaultJPA(String type) {
		if (JTA.equals(type)) {
			return new DefaultJTA().getEntityManager();
		}
		return new DefaultResourceLocal().getEntityManager();
	}
	private EntityManager hibernate(String type) {
		if (JTA.equals(type)) {
			return new HibernateJTA().getEntityManager();
		}
		return new HibernateResourceLocal().getEntityManager();
	}
	private EntityManager eclipselink(String type) {
		if (JTA.equals(type)) {
			return new EclipselinkJTA().getEntityManager();
		}
		return new EclipselinkResourceLocal().getEntityManager();
	}
	private EntityManager openjpa(String type) {
		if (JTA.equals(type)) {
			return new OpenJPAJTA().getEntityManager();
		}
		return new OpenJPAResourceLocal().getEntityManager();
	}


	private class DefaultJTA {
		@PersistenceContext(unitName = "BookStore2") private EntityManager em;
		public EntityManager getEntityManager() {
			log.log(Level.INFO, "entityManager=BookStore2");
			return em;
		}
	}
	private class DefaultResourceLocal {
		@PersistenceUnit(name = "BookStore") private EntityManagerFactory emf;
		public EntityManager getEntityManager() {
			log.log(Level.INFO, "entityManager=BookStore");
			return emf.createEntityManager();
		}
	}

	public static class HibernateJTA {
		@PersistenceContext(unitName = "BookStore-Hibernate-JTA") private EntityManager em;
		public EntityManager getEntityManager() {
			return em;
		}
	}
	public static class HibernateResourceLocal {
		@PersistenceUnit(name = "BookStore-Hibernate-Resource_Local") private EntityManagerFactory emf;
		public EntityManager getEntityManager() {
			return emf.createEntityManager();
		}
	}
	public static class EclipselinkJTA {
		@PersistenceContext(unitName = "BookStore-Eclipselink-JTA") private EntityManager em;
		public EntityManager getEntityManager() {
			return em;
		}
	}
	public static class EclipselinkResourceLocal {
		@PersistenceUnit(name = "BookStore-Eclipselink-Resource_Local") private EntityManagerFactory emf;
		public EntityManager getEntityManager() {
			return emf.createEntityManager();
		}
	}
	public static class OpenJPAJTA {
		@PersistenceContext(unitName = "BookStore-OpenJPA-JTA") private EntityManager em;
		public EntityManager getEntityManager() {
			return em;
		}
	}
	public static class OpenJPAResourceLocal {
		@PersistenceUnit(name = "BookStore-OpenJPA-Resource_Local") private EntityManagerFactory emf;
		public EntityManager getEntityManager() {
			return emf.createEntityManager();
		}
	}

}
