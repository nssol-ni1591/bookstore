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
 * ServletはScopeアノテーションを付加するとCDI管理下になる
 * @Injectの@Qualifierでservice層を切替える
 */
@WebServlet(urlPatterns="/LoginServlet4")
@RequestScoped
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	// ServeletでServiceクラスを@Injectすると、
	// ServiceクラスはJPA用のDAOを@Injectしているので、
	// Service生成時にJPASelectorは生成済みである必要がある
	/*
	 * JPASelectorの初期化：
	 * service層のEntityManagerへの設定は、Service層クラスの@PostConstructで行われるため、
	 * service層クラスをDIするservletクラスの初期化時に行われる/
	 * よって、servletクラスの初期化時ではjpaModule/txTypeの値が未設定のためnull例外が発生する.
	 * 
	 * このため、JPASelectorの仕組みを利用できないので、
	 * JPASelectorを使用しないserviceクラス、
	 * つまり、使用するJPAモジュールが固定化されているService層なら利用できる
	 * 例えば、@UsedClassic
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
			// sessionの初期化
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
