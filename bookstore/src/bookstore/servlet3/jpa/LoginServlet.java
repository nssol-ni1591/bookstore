package bookstore.servlet3.jpa;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.annotation.UsedWeld;
import bookstore.jsf.bean.CommonJSFBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.util.Messages;
import bookstore.vbean.VBook;

/*
 * Servlet��Scope�A�m�e�[�V������t�������CDI�Ǘ����ɂȂ�
 * @Inject��@Qualifier��service�w��ؑւ���
 */
@WebServlet(urlPatterns="/LoginServlet3")
@RequestScoped
public class LoginServlet extends HttpServlet {

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

	@Inject private CommonJSFBean common;

	@Inject private Logger log;


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		HttpSession httpSession = req.getSession(false);
		if (httpSession != null) {
			// session�̏�����
			httpSession.invalidate();
		}
		httpSession = req.getSession();

		// CommonJSFBean�̏�����
		String jpaModule = req.getParameter("jpaModule");
		String txType = req.getParameter("txType");
		common.setJpaModule(jpaModule);
		common.setTxType(txType);
		log.log(Level.INFO, "jpaModule={0}, txType={1}", new Object[] { common.getJpaModule(), common.getTxType() });

		String account = req.getParameter("account");
		String passwd = req.getParameter("passwd");
		Messages errors = new Messages(req);
		RequestDispatcher dispatcher;
		try {
			// check password
			if (!customerService.isPasswordMatched(account, passwd)) {
				// Account mismatched
				errors.add("illegallogin", "error.login.pwmismatch");
				dispatcher = req.getRequestDispatcher("Login3.jsp");
			}
			else {
				httpSession.setAttribute("Login", account);
				List<String> productListAll = bookService.getAllBookISBNs();
				List<VBook> vProductList = bookService.createVBookList(productListAll, null);
				//log.log(Level.INFO, "doPost: productListAll={0}", productListAll)
				//log.log(Level.INFO, "doPost: ProductListView={0}", vProductList)
				httpSession.setAttribute("ProductList", productListAll);
				httpSession.setAttribute("ProductListView", vProductList);
				dispatcher = req.getRequestDispatcher("BookStore3.jsp");
			}
		}
		catch (SQLException e) {
			log.log(Level.SEVERE, "", e);
			errors.add("illegallogin", "error.system.exception");
			dispatcher = req.getRequestDispatcher("Login3.jsp");
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			log.log(Level.SEVERE, "", e);
		}
	}

}
