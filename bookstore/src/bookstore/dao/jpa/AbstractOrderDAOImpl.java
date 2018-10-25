package bookstore.dao.jpa;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import bookstore.dao.OrderDAO;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;
import bookstore.persistence.JPASelector;

public abstract class AbstractOrderDAOImpl<T extends EntityManager> implements OrderDAO<T> {

	/*
	 * transaction-type��JTA��RESOURCE_LOCAL�Ƃ̈Ⴂ�́A
	 * EntityManager�̎擾���@�̈Ⴂ����
	 */
	protected abstract EntityManager getEntityManager();
	protected abstract Logger getLogger();

	@Inject JPASelector selector;


	@Override
	public List<TOrder> retrieveOrders(T em2, List<String> orderIdList) {
		EntityManager em = em2 != null ? em2 : getEntityManager();

		Query q;
		if (orderIdList == null) {
			q = em.createQuery("select o from TOrder o");
			@SuppressWarnings("unchecked")
			List<TOrder> resultList = q.getResultList();
			return resultList;
		}

		q = em.createQuery("select o from TOrder o where o.id in ( :ID )");
		q.setParameter("ID", orderIdList);
		@SuppressWarnings("unchecked")
		List<TOrder> orders = q.getResultList();
		return orders;
	}

	@Override
	public TOrder createOrder(T em2, TCustomer customer) {
		EntityManager em = em2 != null ? em2 : getEntityManager();
		getLogger().log(Level.INFO, "entityManager={0}", em);

		TOrder order = new TOrder();
		order.setOrderday(Timestamp.valueOf(LocalDateTime.now()));
		order.setTCustomer(customer);
		em.persist(order);

		String hql;
		if (selector.isHibernate()) {
			hql = "select o from TOrder o where o.id = (select max(o2.id) from TOrder o2 where o2.TCustomer = :CUSTID)";
		}
		else {
			hql = "select o from TOrder o where o.id = (select max(o2.id) from TOrder o2 where o2.tCustomer = :CUSTID)";
		}
		Query q = em.createQuery(hql);
		q.setParameter("CUSTID", customer);
		order = (TOrder) q.getSingleResult();

		getLogger().log(Level.INFO, "customer_id={0}, order_id={1}"
				, new Object[] { customer.getId(), order.getId() });
		return order;
	}

}
