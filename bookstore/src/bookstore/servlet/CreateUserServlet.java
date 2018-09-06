package bookstore.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.logic.CustomerLogic;
import bookstore.logic.pojo.CustomerLogicWrapper;
import bookstore.util.Messages;

/*
 * Logic LayerÇÃéQè∆Ç≈DAOÇêÿë÷Ç¶ÇÈ
 * ÅEjdbc native - bookstore.logic.wrapper.xxxxLogicWrapper
 * ÅEeclipselink - bookstore.logic.jpa.xxxxLogicWrapper
 */
public class CreateUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String ILLEGAL_CREATE_USER = "illegalCreateUser";
	private static final String CREATE_ACCOUNT_JSP = "createAccount.jsp";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {

		String account = req.getParameter("account");
		String passwd = req.getParameter("passwd");
		String passwd2 = req.getParameter("passwd2");
		String name = req.getParameter("name");
		String email = req.getParameter("email");

		CustomerLogic customerLogic = new CustomerLogicWrapper();
		Messages errors = new Messages(req);

		RequestDispatcher dispatcher;

		if (account == null || account.isEmpty()
				|| passwd == null || passwd.isEmpty()
				|| passwd2 == null || passwd2.isEmpty()
				|| name == null || name.isEmpty()
				|| email == null || email.isEmpty()
				) {
			// check empty field
			errors.add(ILLEGAL_CREATE_USER, "error.createuser.hasempty");
			dispatcher = req.getRequestDispatcher(CREATE_ACCOUNT_JSP);
		}
		else if (!passwd.equals(passwd2)) {
			// passwd and passwd2 not matched
			errors.add(ILLEGAL_CREATE_USER, "error.createuser.pass2inmatch");
			dispatcher = req.getRequestDispatcher(CREATE_ACCOUNT_JSP);
		}
		else if (customerLogic.isAlreadyExsited(account)) {
			// user has already exsited
			errors.add(ILLEGAL_CREATE_USER, "error.createuser.useralreadyexist");
			dispatcher = req.getRequestDispatcher(CREATE_ACCOUNT_JSP);
		}
		else if (!customerLogic.createCustomer(account, passwd, name, email)) {
			// user was not created
			errors.add(ILLEGAL_CREATE_USER, "error.createuser.cannotcreate");
			dispatcher = req.getRequestDispatcher(CREATE_ACCOUNT_JSP);
		}
		else {
			dispatcher = req.getRequestDispatcher("Login.jsp");
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			Logger.getLogger(CreateUserServlet.class.getName()).log(Level.SEVERE, "", e);
		}
	}
}