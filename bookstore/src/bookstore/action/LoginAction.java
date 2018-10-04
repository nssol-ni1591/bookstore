package bookstore.action;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import bookstore.action.bean.LoginActionFormBean;
import bookstore.service.BookService;
import bookstore.service.CustomerService;
import bookstore.vbean.VBook;

public class LoginAction extends Action {

	private CustomerService customerService;
	private BookService bookService;

	@Override
	public ActionForward execute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest req,
			HttpServletResponse res) throws SQLException {

		LoginActionFormBean lafb = (LoginActionFormBean) form;

		// check password
		if (!customerService.isPasswordMatched(lafb.getAccount(), lafb.getPasswd())) {
			// Account Mismached
			ActionMessages errors = new ActionMessages();
			errors.add("illegallogin", new ActionMessage("error.login.pwmismatch"));
			saveMessages(req, errors);
			return mapping.findForward("illegalLogin");
		}

		// getSession()
		HttpSession httpSession = req.getSession(false);
		if (httpSession != null) {
			httpSession.invalidate();
		}

		httpSession = req.getSession();

		httpSession.setAttribute("Login", lafb.getAccount());

		List<String> productListAll = bookService.getAllBookISBNs();
		List<VBook> vProductList = bookService.createVBookList(productListAll, null);

		httpSession.setAttribute("ProductList", productListAll);
		httpSession.setAttribute("ProductListView", vProductList);

		return (mapping.findForward("LoginSuccess"));
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
}