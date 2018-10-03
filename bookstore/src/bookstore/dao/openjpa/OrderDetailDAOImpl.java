package bookstore.dao.openjpa;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.OrderDetailDAO;
import bookstore.pbean.TBook;
import bookstore.pbean.TOrder;
import bookstore.pbean.TOrderDetail;

@UsedOpenJpa
@Dependent
public class OrderDetailDAOImpl<T extends EntityManager> implements OrderDetailDAO<T> {
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
	public void createOrderDetail(final T em2, TOrder inOrder, TBook inBook) throws SQLException {
		EntityManager em = em2 != null ? em2 : em3;

		log.log(Level.INFO, "em={0}", em);
		log.log(Level.INFO, "order_id={0}, book_id={1}"
				, new Object[] { inOrder.getId(), inBook.getId() });

		if ("0-0000-0000-0".equals(inBook.getIsbn())) {
			throw new SQLException("isdn: 0-0000-0000-0");
		}

		TOrderDetail orderDetail = new TOrderDetail();
		orderDetail.setTOrder(inOrder);
		orderDetail.setTBook(inBook);
		em.persist(orderDetail);
	}

}
