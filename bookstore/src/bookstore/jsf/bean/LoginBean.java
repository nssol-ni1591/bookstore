package bookstore.jsf.bean;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import bookstore.annotation.UsedWeld;
import bookstore.logic.BookLogic;
import bookstore.logic.CustomerLogic;
import bookstore.util.Messages;

@Named
@RequestScoped
public class LoginBean {

	@Inject @UsedWeld private CustomerLogic customerLogic;
	@Inject @UsedWeld private BookLogic bookLogic;

	private String uid;
	private String passwd;

	private static final boolean DEBUG = false;

	public String getUid() {
		if (DEBUG)
			System.out.println("LoginBean.getUid: uid=" + uid + ", this=" + this);
		return uid;
	}
	public void setUid(String uid) {
		if (DEBUG)
			System.out.println("LoginBean.setUid: uid=" + uid + ", this=" + this);
		this.uid = uid;
	}

	public String getPasswd() {
		if (DEBUG)
			System.out.println("LoginBean.getPasswd: passwd=" + passwd + ", this=" + this);
		return passwd;
	}
	public void setPasswd(String passwd) {
		if (DEBUG)
			System.out.println("LoginBean.setPasswd: passwd=" + passwd + ", this=" + this);
		this.passwd = passwd;
	}

	public String login() {
		System.out.println("LoginBean.login: uid=" + uid + ", passwd=" + passwd + ", this=" + this);

		// getSession()
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);

		// password match
		if (!customerLogic.isPasswordMatched(getUid(), getPasswd())) {
			// Account mismatched
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Messages.getMessage("error.login.pwmismatch"), "[error.login.pwmismatch]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "Login";
		}
		session.setAttribute("Login", uid);
		return "BookStore";
	}

}
