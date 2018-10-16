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
import bookstore.service.BookService;
import bookstore.util.Messages;

/*
 * Servlet��Scope�A�m�e�[�V������t�������CDI�Ǘ����ɂȂ�
 * @Inject��@Qualifier��service�w��ؑւ���
 */
@WebServlet(urlPatterns="/CheckoutServlet3")
@RequestScoped
public class CheckoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject @UsedWeld private BookService bookService;
	@Inject private Logger log;


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		HttpSession httpSession = req.getSession(false);
		Messages errors = new Messages(req);
		RequestDispatcher dispatcher;

		if (httpSession == null) {
			dispatcher = req.getRequestDispatcher("sessionError.html");
		}
		else {
			@SuppressWarnings("unchecked")
			List<String> selectedItems = (List<String>) httpSession.getAttribute("Cart");

			if (selectedItems == null || selectedItems.isEmpty()) {
				errors.add("productalart", "error.checkout.noselected");
				dispatcher = req.getRequestDispatcher("BookStore3.jsp");
			}
			else {
				try {
					httpSession.setAttribute("ItemsToBuy", bookService.createVCheckout(selectedItems));
					dispatcher = req.getRequestDispatcher("Check3.jsp");
				}
				catch (SQLException e) {
					log.log(Level.SEVERE, "", e);
					errors.add("productalart", "error.system.exception");
					dispatcher = req.getRequestDispatcher("BookStore3.jsp");
				}
			}
		}
		try {
			dispatcher.forward(req, res);
		}
		catch (ServletException | IOException e) {
			log.log(Level.SEVERE, "", e);
		}
	}
}
