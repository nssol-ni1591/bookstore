package bookstore.pbean;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the T_ORDER database table.
 * 
 */
@Entity
@Table(name="T_ORDER")
@NamedQuery(name="TOrder.findAll", query="SELECT t FROM TOrder t")
public class TOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(insertable=false, unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private Timestamp orderday;

	//uni-directional many-to-one association to TCustomer
	@ManyToOne
	@JoinColumn(name="CUSTOMER_ID_FK", nullable=false)
	private TCustomer tCustomer;

	public TOrder() {
		// Do nothing
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getOrderday() {
		return this.orderday;
	}

	public void setOrderday(Timestamp orderday) {
		this.orderday = orderday;
	}

	public TCustomer getTCustomer() {
		return this.tCustomer;
	}

	public void setTCustomer(TCustomer tCustomer) {
		this.tCustomer = tCustomer;
	}

}