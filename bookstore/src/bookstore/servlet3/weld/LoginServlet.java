package bookstore.servlet3.weld;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import bookstore.annotation.UsedWeld;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.servlet3.AbstractLoginServlet;

/*
 * Servlet��Scope�A�m�e�[�V������t�������CDI�Ǘ����ɂȂ�
 * @Inject��@Qualifier��service�w��ؑւ���
 */
@WebServlet(urlPatterns="/LoginServlet3")
@RequestScoped
public class LoginServlet extends AbstractLoginServlet {

	private static final long serialVersionUID = 1L;

	// Servelet��Service�N���X��@Inject����ƁA
	// Service�N���X��JPA�p��DAO��@Inject���Ă���̂ŁA
	// Service��������JPASelector�͐����ς݂ł���K�v������
	/*
	 * JPASelector�̏������F
	 * service�w��EntityManager�ւ̐ݒ�́AService�w�N���X��@PostConstruct�ōs���邽�߁A
	 * service�w�N���X��DI����servlet�N���X�̏��������ɍs����/
	 * ����āAservlet�N���X�̏��������ł�jpaModule/txType�̒l�����ݒ�̂���null��O����������.
	 * 
	 * ���̂��߁AJPASelector�̎d�g�݂𗘗p�ł��Ȃ��̂ŁA
	 * JPASelector���g�p���Ȃ�service�N���X�A
	 * �܂�A�g�p����JPA���W���[�����Œ艻����Ă���Service�w�Ȃ痘�p�ł���
	 * �Ⴆ�΁A@UsedClassic
	 * 
	 * 20181015:
	 * JPASelector����getEntityManager()��call�^�C�~���O��@PostConstractor����
	 * getEntityManager()�̎����ɕύX���邱�Ƃ�null��O�̖������
	 * 
	 * �������Aservlet���ōX�V����common�́AcustomerService��bookService����Ăяo����
	 * JPASelector���Q�Ƃ���common�̃C���X�^���X���قȂ�悤���H
	 */
	@Inject @UsedWeld private CustomerService customerService;
	@Inject @UsedWeld private BookService bookService;
	@Inject private Logger log;


	@Override
	protected BookService getBookService() {
		return bookService;
	}
	@Override
	protected CustomerService getCustomerService() {
		return customerService;
	}
	@Override
	protected Logger getLogger() {
		return log;
	}
	@Override
	protected String getNextPage() {
		return "BookStore4.jsp";
	}
	@Override
	protected String getErrorPage() {
		return "Login4.jsp";
	}

}
