package bookstore.jsf.bean;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import bookstore.annotation.UsedWeld;
import bookstore.logic.CustomerLogic;
import bookstore.util.Messages;

@Named
@RequestScoped
public class AccountBean {

	@Inject @UsedWeld private CustomerLogic customerLogic;

	private String account;
	private String name;
	private String email;
	private String passwd;
	private String passwd2;

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

	public String create() {
		if (account == null || account.isEmpty()
				|| passwd == null || passwd.isEmpty()
				|| passwd2 == null || passwd2.isEmpty()
				|| name == null || name.isEmpty()
				|| email == null || email.isEmpty()
				) {
			// check empty field
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Messages.getMessage("error.createuser.hasempty"),
					"[error.createuser.hasempty]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "CreateAccount";
		}
		if (passwd.equals(passwd2) == false) {
			// passwd and passwd2 not matched
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Messages.getMessage("error.createuser.pass2inmatch"),
					"[error.createuser.pass2inmatch]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "CreateAccount";
		}
		if (customerLogic.isAlreadyExsited(account)) {
			// user has already exsited
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Messages.getMessage("error.createuser.useralreadyexist"),
					"[error.createuser.useralreadyexist]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "CreateAccount";
		}
		if (!customerLogic.createCustomer(account, passwd, getName(), getEmail())) {
			// user was not created
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Messages.getMessage("error.createuser.cannotcreate"),
					"[error.createuser.cannotcreate]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "CreateAccount";
		}
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
				Messages.getMessage("info.createuser.success"),
				"[info.createuser.success]です。");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, fm);
		return "BookStore";
	}

}
