package bookstore.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import bookstore.action.bean.CreateUserActionFormBean;
import bookstore.logic.CustomerLogic;

public class CreateUserAction extends Action {

	private static final String ILLEGAL_CREATE_USER = "illegalCreateUser";

	private CustomerLogic customerLogic;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) {

		CreateUserActionFormBean cuafb = (CreateUserActionFormBean) form;

		String account = cuafb.getAccount();
		String passwd = cuafb.getPasswd();
		String passwd2 = cuafb.getPasswd2();

		ActionMessages errors;

		if (!passwd.equals(passwd2)) {
			// passwd and passwd2 not matched
			errors = new ActionMessages();
			errors.add("illegalcreateuser", new ActionMessage("error.createuser.pass2inmatch"));
			saveMessages(req, errors);
			return (mapping.findForward(ILLEGAL_CREATE_USER));
		}

		if (customerLogic.isAlreadyExsited(account)) {
			// user has already exsited
			errors = new ActionMessages();
			errors.add("illegalcreateuser", new ActionMessage("error.createuser.useralreadyexist"));
			saveMessages(req, errors);
			return (mapping.findForward(ILLEGAL_CREATE_USER));
		}

		if (!customerLogic.createCustomer(account, passwd, cuafb.getName(), cuafb.getEmail())) {
			// user was not created
			errors = new ActionMessages();
			errors.add("illegalcreateuser", new ActionMessage("error.createuser.cannotcreate"));
			saveMessages(req, errors);
			return (mapping.findForward(ILLEGAL_CREATE_USER));
		}

		return (mapping.findForward("UserCreated"));
	}

	public void setCustomerLogic(CustomerLogic inCustomerLogic) {
		this.customerLogic = inCustomerLogic;
	}
}