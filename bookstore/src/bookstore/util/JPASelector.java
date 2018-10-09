package bookstore.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

public class JPASelector {

	//JTAでは@PersistenceContextを使用する
	//RESOURCE_LOCALでは@PersistenceUnitを使用する

	public static final String DEFAULT = "Default";
	public static final String HIBERNATE = "Hibernate";
	public static final String ECLIPSELINK = "Eclipselink";
	public static final String OPENJPA = "OpenJPA";

	public static final String JTA = "JTA";
	public static final String RESOURCE_LOCAL = "RESOURCE_LOCAL";

	public static EntityManager getEntityManager(String type) {
		return getEntityManager(null, type);
	}
	public static EntityManager getEntityManager(String jpaModule, String type) {
		if (!JTA.equals(type) && !RESOURCE_LOCAL.equals(type)) {
			throw new IllegalArgumentException(type + " is unknown type");
		}
		if (jpaModule == null) {
			return new JPASelector().defaultJPA(type);
		}

		switch (jpaModule) {
		case HIBERNATE:
			return new JPASelector().hibernate(type);
		case ECLIPSELINK:
			return new JPASelector().eclipselink(type);
		case OPENJPA:
			return new JPASelector().openjpa(type);
		default:
			throw new IllegalArgumentException(jpaModule + " is unknown module");
		}
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
			return em;
		}
	}
	private class DefaultResourceLocal {
		@PersistenceUnit(name = "BookStore") private EntityManagerFactory emf;
		public EntityManager getEntityManager() {
			return emf.createEntityManager();
		}
	}

	private class HibernateJTA {
		@PersistenceContext(unitName = "BookStore-Hibernate-JTA") private EntityManager em;
		public EntityManager getEntityManager() {
			return em;
		}
	}
	private class HibernateResourceLocal {
		@PersistenceUnit(name = "BookStore-Hibernate-Resource_Local") private EntityManagerFactory emf;
		public EntityManager getEntityManager() {
			return emf.createEntityManager();
		}
	}
	private class EclipselinkJTA {
		@PersistenceContext(unitName = "BookStore-Eclipselink-JTA") private EntityManager em;
		public EntityManager getEntityManager() {
			return em;
		}
	}
	private class EclipselinkResourceLocal {
		@PersistenceUnit(name = "BookStore-Eclipselink-Resource_Local") private EntityManagerFactory emf;
		public EntityManager getEntityManager() {
			return emf.createEntityManager();
		}
	}
	private class OpenJPAJTA {
		@PersistenceContext(unitName = "BookStore-OpenJPA-JTA") private EntityManager em;
		public EntityManager getEntityManager() {
			return em;
		}
	}
	private class OpenJPAResourceLocal {
		@PersistenceUnit(name = "BookStore-OpenJPA-Resource_Local") private EntityManagerFactory emf;
		public EntityManager getEntityManager() {
			return emf.createEntityManager();
		}
	}

}
