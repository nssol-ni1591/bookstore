package bookstore.logic.ejb.cmt;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedOpenJpa;
import bookstore.dao.CustomerDAO;
import bookstore.logic.CustomerLogic;
import bookstore.logic.AbstractCustomerLogic;

@Stateless(name="CustomerLogicCmtWrapper")
@LocalBean
@Local(CustomerLogic.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CustomerLogicWrapper extends AbstractCustomerLogic<EntityManager> {

	@Inject @UsedOpenJpa CustomerDAO<EntityManager> customerdao;
	@Inject private Logger log;

	@Override
	protected CustomerDAO<EntityManager> getCustomerDAO() {
		return customerdao;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected EntityManager getManager() {
		return null;
		// @TransactionAttribute�ŊǗ�����邽�߁Aem�������p���K�v�͂Ȃ�
	}

	/*
	 * Container-Managed Transactions�����[���o�b�N����̂�2�̏ꍇ������
	 * (1)�V�X�e����O��������ꂽ�ꍇ�A�R���e�i�͎����I�Ƀg�����U�N�V���������[���o�b�N���܂�
	 * (2)EJBContext�C���^�[�t�F�C�X��setRollbackOnly���\�b�h���Ăяo���ꂽ�ꍇ
	 * ���V�X�e����O�Fjava.lang.RuntimeException�܂���java.rmi.RemoteException ���g�������O
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean createCustomer(String inUid, String inPassword, String inName, String inEmail) throws Exception {
		try {
			boolean rc = super.createCustomer(inUid, inPassword, inName, inEmail);
			log.log(Level.INFO, "rc={0}", rc);
			return rc;
		}
		catch (RuntimeException | RemoteException e) {
			throw e;
		}
		catch (Exception e) {
			// EJBException�̓V�X�e����O
			throw new EJBException(e);
		}
	}

}