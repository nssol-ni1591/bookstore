package bookstore.action.bean;

import org.apache.struts.action.ActionForm;

public class LoginActionFormBean extends ActionForm{
	
	private String account;
	private String passwd;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}