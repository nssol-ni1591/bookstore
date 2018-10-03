package bookstore.dao.openjpa;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.OrderDAO;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

@UsedOpenJpa
@Dependent
public class OrderDAOImpl<T extends EntityManager> implements OrderDAO<T> {
	/*
	 * RESOURCE_LOCAL��JTA�i���R���e�L�X�g�̔�r
	 * <persistence-unit transaction-type = "RESOURCE_LOCAL">���g�p����ƁA
	 * EntityManager�iPersistenceContext / Cache�j�̍쐬�ƒǐՂ��s���܂��B
	 * 
	 * EntityManagerFactory���g�p����EntityManager���擾����K�v������܂�
	 * ���������EntityManager�C���X�^���X�� �APersistenceContext / Cache�ł��B
	 * @PersistenceUnit���߂�����EntityManagerFactory��}���ł��܂��i@PersistenceContext�ł͂���܂���j
	 * 
	 * @PersistenceContext���g�p����RESOURCE_LOCAL�^�C�v�̃��j�b�g���Q�Ƃ��邱�Ƃ͂ł��܂���
	 * EntityManger�ւ̂��ׂĂ̌Ăяo���̑O���EntityTransaction API���g�p���ĊJ�n/�R�~�b�g����K�v������܂�
	 * 
	 * entityManagerFactory.createEntityManager�i�j��2��Ăяo���ƁA 2�̕ʁX��EntityManager
	 * �C���X�^���X����������A���̂��߂�2�̕ʁX��PersistenceContext / Cache����������܂��B
	 * 
	 * EntityManager�̕����̃C���X�^���X���g�p���邱�Ƃ������߂��܂�
	 * �i���ӁF�ŏ��̃C���X�^���X��j�����Ȃ�����A2�ڂ̃C���X�^���X���쐬���Ȃ��ł��������j
	 */
	//openjpa�ł�JTA���g�p���Ă���̂�@PersistenceContext���g�p����
	@PersistenceContext(unitName = "BookStore2") private EntityManager em3;
	@Inject private Logger log;

	@Override
	public List<TOrder> retrieveOrders(final T em2, List<String> orderIdList) {
		EntityManager em = em2 != null ? em2 : em3;
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
	public TOrder createOrder(final T em2, TCustomer inCustomer) {
		EntityManager em = em2 != null ? em2 : em3;
		log.log(Level.INFO, "em={0}", em);

		TOrder order = new TOrder();
		order.setOrderday(Timestamp.valueOf(LocalDateTime.now()));
		order.setTCustomer(inCustomer);
		em.persist(order);
		/*
		Query q = em.createNativeQuery("select AUTOINCREMENTVALUE" + 
				" from sys.systables t, sys.syscolumns c" + 
				" where t.tablename='T_ORDER'" + 
				"   and c.referenceid=t.tableid" + 
				"   and c.columnname='ID'");
		Long id = (Long) q.getSingleResult();
		order.setId(id.intValue());
		*/
		Query q = em.createNativeQuery(
				"select max(id)" + 
				" from t_order" + 
				" where customer_id_fk=?"
				);
		q.setParameter(1, inCustomer.getId());
		int id = (int) q.getSingleResult();

		log.log(Level.INFO, "id={0}, order_id={1}", new Object[] { id, order.getId() });

		Query q2 = em.createQuery("select o from TOrder o where o.id=:ID");
		q2.setParameter("ID", id);
		order = (TOrder) q2.getSingleResult();

		log.log(Level.INFO, "customer_id={0}, order_id={1}"
				, new Object[] { inCustomer.getId(), order.getId() });
		return order;
	}

}
