package bookstore.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.logic.CustomerLogic;
import bookstore.logic.CustomerLogicImpl2;
import bookstore.util.Messages;

public class CreateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String account = req.getParameter("account");
		String passwd = req.getParameter("passwd");
		String passwd2 = req.getParameter("passwd2");
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		CustomerLogic customerLogic = new CustomerLogicImpl2();
		Messages errors = new Messages();

		RequestDispatcher dispatcher;

		if (account == null || account.isEmpty()
				|| passwd == null || passwd.isEmpty()
				|| passwd2 == null || passwd2.isEmpty()
				|| name == null || name.isEmpty()
				|| email == null || email.isEmpty()
				) {
			// check empty field
			errors.add("illegalcreateuser", "error.createuser.hasempty");
			req.setAttribute("errors", errors);
			dispatcher = req.getRequestDispatcher("createAccount.jsp");
		}
		else if (passwd.equals(passwd2) == false) {
			// passwd and passwd2 not matched
			errors.add("illegalcreateuser", "error.createuser.pass2inmatch");
			req.setAttribute("errors", errors);
			dispatcher = req.getRequestDispatcher("createAccount.jsp");
		}
		else if (customerLogic.isAlreadyExsited(account)) {
			// user has already exsited
			errors.add("illegalcreateuser", "error.createuser.useralreadyexist");
			req.setAttribute("errors", errors);
			dispatcher = req.getRequestDispatcher("createAccount.jsp");
		}
		else if (!customerLogic.createCustomer(account, passwd, name, email)) {
			// user was not created
			errors.add("illegalcreateuser", "error.createuser.cannotcreate");
			req.setAttribute("errors", errors);
			dispatcher = req.getRequestDispatcher("createAccount.jsp");
		}
		else {
			dispatcher = req.getRequestDispatcher("index.jsp");
		}
		dispatcher.forward(req, res);
	}
}