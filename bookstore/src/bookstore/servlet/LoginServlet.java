package bookstore.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.service.pojo.BookServiceWrapper;
import bookstore.service.pojo.CustomerServiceWrapper;
import bookstore.util.Messages;
import bookstore.vbean.VBook;

/*
 * Logic LayerÇÃéQè∆Ç≈DAOÇêÿë÷Ç¶ÇÈ
 * ÅEjdbc native - bookstore.logic.wrapper.xxxxLogicWrapper
 * ÅEeclipselink - bookstore.logic.jpa.xxxxLogicWrapper
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log =  Logger.getLogger(LoginServlet.class.getName());

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {

		String account = req.getParameter("account");
		String passwd = req.getParameter("passwd");

		CustomerService customerLogic = new CustomerServiceWrapper();
		Messages errors = new Messages(req);

		RequestDispatcher dispatcher;

		try {
		// check password
		if (!customerLogic.isPasswordMatched(account, passwd)) {
			// Account mismatched
			errors.add("illegallogin", "error.login.pwmismatch");
			dispatcher = req.getRequestDispatcher("Login.jsp");
		}
		else {
			BookService bookLogic = new BookServiceWrapper();

			// getSession()
			HttpSession httpSession = req.getSession(false);
			if (httpSession != null) {
				httpSession.invalidate();
			}

			httpSession = req.getSession();

			httpSession.setAttribute("Login", account);

			List<String> productListAll = bookLogic.getAllBookISBNs();
			List<VBook> vProductList = bookLogic.createVBookList(productListAll, null);

			httpSession.setAttribute("ProductList", productListAll);
			httpSession.setAttribute("ProductListView", vProductList);

			log.log(Level.INFO, "doPost: productListAll={0}", productListAll);
			log.log(Level.INFO, "doPost: ProductListView={0}", vProductList);

			dispatcher = req.getRequestDispatcher("BookStore.jsp");
		}
		}
		catch (SQLException e) {
			log.log(Level.SEVERE, "", e);
			errors.add("illegallogin", "error.system.exception");
			dispatcher = req.getRequestDispatcher("Login.jsp");
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			log.log(Level.SEVERE, "", e);
		}
	}

}
