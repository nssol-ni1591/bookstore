package bookstore.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.logic.BookLogic;
import bookstore.logic.CustomerLogic;
import bookstore.logic.pojo.BookLogicWrapper;
import bookstore.logic.pojo.CustomerLogicWrapper;
import bookstore.util.Messages;
import bookstore.vbean.VBook;

/*
 * Logic LayerÇÃéQè∆Ç≈DAOÇêÿë÷Ç¶ÇÈ
 * ÅEjdbc native - bookstore.logic.wrapper.xxxxLogicWrapper
 * ÅEeclipselink - bookstore.logic.jpa.xxxxLogicWrapper
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {

		String account = req.getParameter("account");
		String passwd = req.getParameter("passwd");

		CustomerLogic customerLogic = new CustomerLogicWrapper();
		Messages errors = new Messages(req);

		RequestDispatcher dispatcher;

		// check password
		if (!customerLogic.isPasswordMatched(account, passwd)) {
			// Account mismatched
			errors.add("illegallogin", "error.login.pwmismatch");

			dispatcher = req.getRequestDispatcher("Login.jsp");
		}
		else {
			BookLogic bookLogic = new BookLogicWrapper();

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

			Logger.getLogger(LoginServlet.class.getName()).log(Level.INFO, "doPost: productListAll={0}", productListAll);
			Logger.getLogger(LoginServlet.class.getName()).log(Level.INFO, "doPost: ProductListView={0}", vProductList);

			dispatcher = req.getRequestDispatcher("BookStore.jsp");
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, "", e);
		}
	}

}
