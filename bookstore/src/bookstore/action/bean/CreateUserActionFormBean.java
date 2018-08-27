package bookstore.action.bean;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class CreateUserActionFormBean extends ActionForm{
	
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
	
	public ActionErrors validate( ActionMapping mapping,
								   HttpServletRequest req ){
		
		ActionErrors errors = null;
		if (getName() == null    || getName().equals( "" ) ||
			getEmail() == null   || getEmail().equals( "" ) ||
			getAccount() == null || getAccount().equals( "" ) ||
			getPasswd() == null  || getPasswd().equals( "" ) ||
			getPasswd2() == null || getPasswd2().equals( "" )){

			errors = new ActionErrors();
		
			errors.add( "illegalcreateuser",
					new ActionMessage( "error.createuser.hasempty" ) );
		}
		
		return( errors );
	}
}