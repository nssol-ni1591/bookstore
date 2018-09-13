package bookstore.jsf.bean;

import java.util.logging.Level;
import java.util.logging.Logger;

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
	//@Inject @UsedWeld private BookLogic bookLogic;
	@Inject private Logger log;

	private String uid;
	private String passwd;

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String login() {
		log.log(Level.INFO, "uid={0}, pw={1}, this={2}"
				, new Object[] { uid, passwd, this });

		// getSession()
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);

		// password match
		if (!customerLogic.isPasswordMatched(getUid(), getPasswd())) {
			// Account mismatched
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.login.pwmismatch")
					, "[error.login.pwmismatch]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return "Login";
		}
		session.setAttribute("Login", uid);
		return "BookStore";
	}

}
