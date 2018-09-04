package bookstore.jsf.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import bookstore.logic.CustomerLogic;
import bookstore.session.SessionBean;

@ManagedBean(name="loginBean")
@Component("loginBean")
@Scope("request")
public class LoginBean {

	private SessionBean session;

	private CustomerLogic customerLogic;

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
		System.out.println("LoginAction.login: this=" + this);

		// password match
		if (!customerLogic.isPasswordMatched(getUid(), getPasswd())) {
			// Account Mismached
			//setMessage("error.login.pwmismatch");
			// new FacesMessage(メッセージレベル, サマリーメッセージ, 詳細メッセージ);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"error.login.pwmismatch", "[error.login.pwmismatch]です。");
			FacesContext fc = FacesContext.getCurrentInstance();
			// addMessage(コンポーネントID, FacesMessage) 関連付けるコンポーネントがない場合はnull
			fc.addMessage(null, fm);
			return "illegalLogin";
		}

		session.setUid(uid);
		return "gotoLogin";
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}
	public void setCustomerLogic(CustomerLogic customerLogic) {
		System.out.println("LoginAction.setCustomerLogic: customerLogic=" + customerLogic);
		this.customerLogic = customerLogic;
	}
}
