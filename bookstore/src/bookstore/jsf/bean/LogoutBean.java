package bookstore.jsf.bean;

import bookstore.session.SessionBean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("logoutBean")
@Scope("request")
public class LogoutBean {

	private SessionBean session;

	public String logout() {
		session.setUid(null);
		return "gotoLogout";
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}

}
