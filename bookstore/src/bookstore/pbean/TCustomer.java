package bookstore.pbean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_CUSTOMER database table.
 * 
 */
@Entity
@Table(name="T_CUSTOMER")
@NamedQuery(name="TCustomer.findAll", query="SELECT t FROM TCustomer t")
public class TCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(insertable=false, unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=64)
	private String email;

	@Column(nullable=false, length=64)
	private String name;

	@Column(nullable=false, length=32)
	private String passwordmd5;

	@Column(nullable=false, length=16)
	private String username;

	public TCustomer() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswordmd5() {
		return this.passwordmd5;
	}

	public void setPasswordmd5(String passwordmd5) {
		this.passwordmd5 = passwordmd5;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}