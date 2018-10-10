package bookstore.jsf.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CommonJSFBean implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String jpaModule;
	protected String txType;

	public String getJpaModule() {
		return jpaModule;
	}
	public void setJpaModule(String jpaModule) {
		this.jpaModule = jpaModule;
	}
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}

	protected String createRequestURL(String baseURL) {
		return String.format("%s?jpaModule=%s&txType=%s", baseURL, jpaModule, txType);
	}
}
