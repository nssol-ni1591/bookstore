package bookstore.dao.jpa;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bookstore.annotation.UsedJpa;

@UsedJpa
@Dependent
public class CustomerDAOImpl<T extends EntityManager> extends AbstractCustomerDAOImpl<T> {

	@Inject private Logger log; 

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
	@Override
	protected EntityManager getEntityManager() {
		return null;
	}

	@Override
	protected Logger getLogger() {
		return log;
	}

}
