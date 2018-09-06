package bookstore.jsf.bean;

import java.util.List;

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
import bookstore.vbean.VBook;

@Named
@RequestScoped
public class LoginBean {

	@Inject @UsedWeld private CustomerLogic customerLogic;
	@Inject @UsedWeld private BookLogic bookLogic;

	private String uid;
	private String passwd;

	public String getUid() {
		System.out.println("LoginBean.getUid: uid=" + uid + ", this=" + this);
		return uid;
	}
	public void setUid(String uid) {
		System.out.println("LoginBean.setUid: uid=" + uid + ", this=" + this);
		this.uid = uid;
	}

	public String getPasswd() {
		System.out.println("LoginBean.getPasswd: passwd=" + passwd + ", this=" + this);
		return passwd;
	}
	public void setPasswd(String passwd) {
		System.out.println("LoginBean.setPasswd: passwd=" + passwd + ", this=" + this);
		this.passwd = passwd;
	}

	public String login() {
		System.out.println("LoginBean.login: uid=" + uid + ", passwd=" + passwd + ", this=" + this);

		// password match
		if (!customerLogic.isPasswordMatched(getUid(), getPasswd())) {
			// Account mismatched
			//setMessage("error.login.pwmismatch");
			// new FacesMessage(���b�Z�[�W���x��, �T�}���[���b�Z�[�W, �ڍ׃��b�Z�[�W);
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"error.login.pwmismatch", "[error.login.pwmismatch]�ł��B");
			FacesContext fc = FacesContext.getCurrentInstance();
			// addMessage(�R���|�[�l���gID, FacesMessage) �֘A�t����R���|�[�l���g���Ȃ��ꍇ��null
			fc.addMessage(null, fm);
			return "Login";
		}
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(true);
		session.setAttribute("Login", uid);

		List<String> productList = bookLogic.getAllBookISBNs();
		List<VBook> productListView = bookLogic.createVBookList(productList, null);

		session.setAttribute("ProductList", productList);
		session.setAttribute("ProductListView", productListView);
		return "BookStore";
	}

}
