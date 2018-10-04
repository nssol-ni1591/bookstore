package bookstore.jsf.bean;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import bookstore.service.CustomerService;
import bookstore.util.Messages;

public abstract class AbstractLoginBean {

	@Inject private Logger log;

	private String uid;
	private String passwd;

	protected abstract CustomerService getCustomerService();
	protected abstract String getLoginPage();
	protected abstract String getBookStorePage();

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

	public String login() throws SQLException {
		log.log(Level.INFO, "uid={0}, pw={1}, this={2}"
				, new Object[] { uid, passwd, this });

		// getSession()
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);

		CustomerService customerService = getCustomerService();
		// password match
		if (!customerService.isPasswordMatched(getUid(), getPasswd())) {
			// Account mismatched
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR
					, Messages.getMessage("error.login.pwmismatch")
					, "[error.login.pwmismatch]Ç≈Ç∑ÅB");
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, fm);
			return getLoginPage();
		}
		session.setAttribute("Login", uid);
		return getBookStorePage();
	}

}
