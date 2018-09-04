package bookstore.jsf.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import bookstore.logic.CustomerLogic;

@Component("accountBean")
@Scope("request")
public class AccountBean {

	private String account;
	private String name;
	private String email;
	private String passwd;
	private String passwd2;

	CustomerLogic customerLogic;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPasswd2() {
		return passwd2;
	}

	public void setPasswd2(String passwd2) {
		this.passwd2 = passwd2;
	}

	/*
	 * public ActionErrors validate(ActionMapping mapping, HttpServletRequest
	 * req) {
	 *
	 * ActionErrors errors = null; if (getName() == null || getName().equals("")
	 * || getEmail() == null || getEmail().equals("") || getAccount() == null ||
	 * getAccount().equals("") || getPasswd() == null || getPasswd().equals("")
	 * || getPasswd2() == null || getPasswd2().equals("")) {
	 *
	 * errors = new ActionErrors();
	 *
	 * errors.add("illegalcreateuser", new ActionMessage(
	 * "error.createuser.hasempty")); }
	 *
	 * return (errors); }
	 */

	public String create() {
		String passwd = getPasswd();
		String passwd2 = getPasswd2();

		if (passwd.equals(passwd2) == false) {
			// passwd and passwd2 not matched
			// errors = new ActionMessages();
			// errors.add( "illegalcreateuser",
			// new ActionMessage( "error.createuser.pass2inmatch" ) );
			// saveMessages( req, errors );
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"error.createuser.pass2inmatch",
					"[error.createuser.pass2inmatch]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "illegalCreateUser";
		}

		String account = getAccount();

		if (customerLogic.isAlreadyExsited(account)) {
			// user has already exsited
			// new ActionMessage( "error.createuser.useralreadyexist" ) );
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"error.createuser.useralreadyexist",
					"[error.createuser.useralreadyexist]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "illegalCreateUser";
		}

		if (!customerLogic.createCustomer(account, passwd, getName(), getEmail())) {
			// user was not created
			//		"error.createuser.cannotcreate"));
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"error.createuser.cannotcreate",
					"[error.createuser.cannotcreate]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "illegalCreateUser";
		}

		return "UserCreated";
	}

	public void setCustomerLogic(CustomerLogic inCustomerLogic) {
		this.customerLogic = inCustomerLogic;
	}
}
