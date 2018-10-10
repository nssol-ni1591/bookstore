package bookstore.jsf.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CommonJSFBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String jpaModule;
	protected String jpaType;

	public String getJpaModule() {
		return jpaModule;
	}
	public void setJpaModule(String jpaModule) {
		this.jpaModule = jpaModule;
	}
	public String getJpaType() {
		return jpaType;
	}
	public void setJpaType(String jpaType) {
		this.jpaType = jpaType;
	}

	protected String createRequestURL(String baseURL) {
		return String.format("%s?jpaModule=%s&jpaType=%s", baseURL, jpaModule, jpaType);
	}
}
