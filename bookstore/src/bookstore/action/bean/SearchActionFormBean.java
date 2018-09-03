package bookstore.action.bean;

import org.apache.struts.action.ActionForm;

public class SearchActionFormBean extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}