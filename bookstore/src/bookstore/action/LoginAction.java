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
import bookstore.logic.BookLogic;
import bookstore.logic.CustomerLogic;
import bookstore.vbean.VBook;

public class LoginAction extends Action {

	private CustomerLogic customerLogic;
	private BookLogic bookLogic;

	@Override
	public ActionForward execute(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest req,
			HttpServletResponse res) throws SQLException {

		LoginActionFormBean lafb = (LoginActionFormBean) form;

		// check password
		if (!customerLogic.isPasswordMatched(lafb.getAccount(), lafb.getPasswd())) {
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

		List<String> productListAll = bookLogic.getAllBookISBNs();
		List<VBook> vProductList = bookLogic.createVBookList(productListAll, null);

		httpSession.setAttribute("ProductList", productListAll);
		httpSession.setAttribute("ProductListView", vProductList);

		return (mapping.findForward("LoginSuccess"));
	}

	public void setCustomerLogic(CustomerLogic customerLogic) {
		this.customerLogic = customerLogic;
	}

	public void setBookLogic(BookLogic bookLogic) {
		this.bookLogic = bookLogic;
	}
}