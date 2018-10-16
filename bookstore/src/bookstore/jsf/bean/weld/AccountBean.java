package bookstore.jsf.bean.weld;

import java.sql.SQLException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import bookstore.annotation.UsedWeld;
import bookstore.service.CustomerService;
import bookstore.util.Messages;

@Named
@RequestScoped
public class AccountBean {

	private static final String CREATE_ACCOUNT = "CreateAccount";

	@Inject @UsedWeld private CustomerService customerService;

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

	public String create() throws SQLException {
		if (account == null || account.isEmpty()
				|| passwd == null || passwd.isEmpty()
				|| passwd2 == null || passwd2.isEmpty()
				|| name == null || name.isEmpty()
				|| email == null || email.isEmpty()
				) {
			// check empty field
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.createuser.hasempty")
					, "[error.createuser.hasempty]�ł��B");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return CREATE_ACCOUNT;
		}
		if (!passwd.equals(passwd2)) {
			// passwd and passwd2 not matched
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.createuser.pass2inmatch")
					, "[error.createuser.pass2inmatch]�ł��B");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return CREATE_ACCOUNT;
		}
		if (customerService.isAlreadyExsited(account)) {
			// user has already exsited
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.createuser.useralreadyexist")
					, "[error.createuser.useralreadyexist]�ł��B");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return CREATE_ACCOUNT;
		}
		if (!customerService.createCustomer(account, passwd, getName(), getEmail())) {
			// user was not created
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.createuser.cannotcreate")
					, "[error.createuser.cannotcreate]�ł��B");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return CREATE_ACCOUNT;
		}
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO
				, Messages.getMessage("info.createuser.success")
				, "[info.createuser.success]�ł��B");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, fm);
		return "BookStore";
	}

}