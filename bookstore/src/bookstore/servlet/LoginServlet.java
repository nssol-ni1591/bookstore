package bookstore.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bookstore.logic.BookLogic;
import bookstore.logic.BookLogicImpl;
import bookstore.logic.BookLogicImpl2;
import bookstore.logic.CustomerLogic;
import bookstore.logic.CustomerLogicImpl;
import bookstore.logic.CustomerLogicImpl2;
import bookstore.vbean.VBook;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// for Spring
	//CustomerLogic customerLogic;
	//BookLogic bookLogic;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String account = req.getParameter("account");
		String passwd = req.getParameter("passwd");

		CustomerLogic customerLogic = new CustomerLogicImpl2();
		Map<String, String> errors = new HashMap<>();

		RequestDispatcher dispatcher;

		// check password
		if (!customerLogic.isPasswordMatched(account, passwd)) {
			// Account mismached
			errors.put("illegallogin", "error.login.pwmismatch");
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

			System.out.println("LoginServlet.doPost: ProductList=" + productListAll);
			System.out.println("LoginServlet.doPost: ProductListView=" + vProductList);

			dispatcher = req.getRequestDispatcher("BookStore.jsp");
			//dispatcher = req.getRequestDispatcher("/BookStore.vm");
		}
		dispatcher.forward(req, res);
	}

	/* for Spring
	public void setCustomerLogic(CustomerLogic customerLogic) {
		this.customerLogic = customerLogic;
	}

	public void setBookLogic(BookLogic bookLogic) {
		this.bookLogic = bookLogic;
	}
	*/
}
