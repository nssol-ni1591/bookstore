package bookstore.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import bookstore.logic.BookLogic;
import bookstore.logic.CustomerLogic;

public class LogoutAction extends Action {

	CustomerLogic customerLogic;
	BookLogic bookLogic;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res) {

		// LogoutActionFormBean lafb = (LogoutActionFormBean)form

		HttpSession httpSession = req.getSession(false);
		if (httpSession == null) {
			return (mapping.findForward("illegalSession"));
		}

		// getSession()
		httpSession.invalidate();

		return (mapping.findForward("LogoutSuccess"));
	}

}
