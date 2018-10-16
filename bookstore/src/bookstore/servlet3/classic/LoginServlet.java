package bookstore.servlet3.classic;

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

import bookstore.annotation.UsedClassic;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.util.Messages;
import bookstore.vbean.VBook;

/*
 * Servlet��Scope�A�m�e�[�V������t�������CDI�Ǘ����ɂȂ�
 * @Inject��@Qualifier��service�w��ؑւ���
 */
@WebServlet(urlPatterns="/LoginServlet4")
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
	 */
	@Inject @UsedClassic CustomerService customerService;
	@Inject @UsedClassic BookService bookService;

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

		String account = req.getParameter("account");
		String passwd = req.getParameter("passwd");
		Messages errors = new Messages(req);
		RequestDispatcher dispatcher;
		try {
			// check password
			if (!customerService.isPasswordMatched(account, passwd)) {
				// Account mismatched
				errors.add("illegallogin", "error.login.pwmismatch");
				dispatcher = req.getRequestDispatcher("Login4.jsp");
			}
			else {
				httpSession = req.getSession();
				httpSession.setAttribute("Login", account);
				List<String> productListAll = bookService.getAllBookISBNs();
				List<VBook> vProductList = bookService.createVBookList(productListAll, null);
				//log.log(Level.INFO, "doPost: productListAll={0}", productListAll)
				//log.log(Level.INFO, "doPost: ProductListView={0}", vProductList)
				httpSession.setAttribute("ProductList", productListAll);
				httpSession.setAttribute("ProductListView", vProductList);
				dispatcher = req.getRequestDispatcher("BookStore4.jsp");
			}
		}
		catch (SQLException e) {
			log.log(Level.SEVERE, "", e);
			errors.add("illegallogin", "error.system.exception");
			dispatcher = req.getRequestDispatcher("Login4.jsp");
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			log.log(Level.SEVERE, "", e);
		}
	}

}
