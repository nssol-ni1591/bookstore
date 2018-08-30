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
import bookstore.logic.BookLogicImpl2;
import bookstore.logic.CustomerLogic;
import bookstore.logic.CustomerLogicImpl2;
import bookstore.util.Messages;
import bookstore.vbean.VBook;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String account = req.getParameter("account");
		String passwd = req.getParameter("passwd");

		CustomerLogic customerLogic = new CustomerLogicImpl2();
		Messages errors = new Messages();

		RequestDispatcher dispatcher;

		// check password
		if (!customerLogic.isPasswordMatched(account, passwd)) {
			// Account mismatched
			errors.add("illegallogin", "error.login.pwmismatch");
			req.setAttribute("errors", errors);

			dispatcher = req.getRequestDispatcher("index.jsp");
		}
		else {
			BookLogic bookLogic = new BookLogicImpl2();

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

			Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, "doPost: productListAll={0}", productListAll);
			Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, "doPost: ProductListView={0}", vProductList);

			dispatcher = req.getRequestDispatcher("BookStore.jsp");
		}
		dispatcher.forward(req, res);
	}

}
