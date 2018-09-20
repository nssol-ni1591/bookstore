package bookstore.pbean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_ORDER_DETAIL database table.
 * 
 */
@Entity
@Table(name="T_ORDER_DETAIL")
@NamedQuery(name="TOrderDetail.findAll", query="SELECT t FROM TOrderDetail t")
public class TOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(insertable=false, unique=true, nullable=false)
	private int id;

	//uni-directional many-to-one association to TBook
	@ManyToOne
	@JoinColumn(name="BOOK_ID_FK", nullable=false)
	private TBook TBook;

	//uni-directional many-to-one association to TOrder
	@ManyToOne
	@JoinColumn(name="ORDER_ID_FK", nullable=false)
	private TOrder TOrder;

	public TOrderDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TBook getTBook() {
		return this.TBook;
	}

	public void setTBook(TBook TBook) {
		this.TBook = TBook;
	}

	public TOrder getTOrder() {
		return this.TOrder;
	}

	public void setTOrder(TOrder TOrder) {
		this.TOrder = TOrder;
	}

}