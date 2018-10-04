package bookstore.jsf.bean.ejb;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import bookstore.service.ejb.cmt.CustomerServiceWrapper;
import bookstore.util.Messages;

@Named(value="accountBean2")
@RequestScoped
public class AccountBean2 {

	private static final String CREATE_ACCOUNT = "CreateAccount2";

	@EJB private CustomerServiceWrapper customerService;

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

	public String create() throws Exception {
		if (account == null || account.isEmpty()
				|| passwd == null || passwd.isEmpty()
				|| passwd2 == null || passwd2.isEmpty()
				|| name == null || name.isEmpty()
				|| email == null || email.isEmpty()
				) {
			// check empty field
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.createuser.hasempty")
					, "[error.createuser.hasempty]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return CREATE_ACCOUNT;
		}
		if (!passwd.equals(passwd2)) {
			// passwd and passwd2 not matched
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.createuser.pass2inmatch")
					, "[error.createuser.pass2inmatch]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return CREATE_ACCOUNT;
		}
		if (customerService.isAlreadyExsited(account)) {
			// user has already exsited
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.createuser.useralreadyexist")
					, "[error.createuser.useralreadyexist]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return CREATE_ACCOUNT;
		}
		if (!customerService.createCustomer(account, passwd, getName(), getEmail())) {
			// user was not created
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.createuser.cannotcreate")
					, "[error.createuser.cannotcreate]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return CREATE_ACCOUNT;
		}
		FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO
				, Messages.getMessage("info.createuser.success")
				, "[info.createuser.success]です。");
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, fm);
		return "BookStore2";
	}

}
