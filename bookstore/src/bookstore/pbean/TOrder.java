package bookstore.pbean;
// Generated 2009/09/17 12:35:57 by Hibernate Tools 3.2.4.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TOrder generated by hbm2java
 */
@Entity
@Table(name="T_ORDER"
    ,schema="BOOKSTORE"
)
public class TOrder  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

     private int id;
     private TCustomer tCustomer;
     private Date orderday;
     private Set<TOrderDetail> tOrderDetails = new HashSet<>(0);

    public TOrder() {
    }

	
    public TOrder(int id, TCustomer tCustomer, Date orderday) {
        this.id = id;
        this.tCustomer = tCustomer;
        this.orderday = orderday;
    }
    public TOrder(int id, TCustomer tCustomer, Date orderday, Set<TOrderDetail> tOrderDetails) {
       this.id = id;
       this.tCustomer = tCustomer;
       this.orderday = orderday;
       this.tOrderDetails = tOrderDetails;
	}

	@Id 
    @Column(name="ID", nullable=false, precision=22, scale=0)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ID_FK", nullable=false)
    public TCustomer getTCustomer() {
        return this.tCustomer;
    }
    
    public void setTCustomer(TCustomer tCustomer) {
        this.tCustomer = tCustomer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ORDERDAY", nullable=false, length=11)
    public Date getOrderday() {
        return this.orderday;
    }
    
    public void setOrderday(Date orderday) {
        this.orderday = orderday;
    }

    //@OneToMany(fetch=FetchType.LAZY, mappedBy="TOrder")
	// mappedbyは、双方向の参照をもつ場合に指定する
	// この場合は、親⇒子への片方向なのでmappedbyは不要？
    @OneToMany(fetch=FetchType.EAGER, mappedBy="TOrder")
    public Set<TOrderDetail> getTOrderDetails() {
        return this.tOrderDetails;
    }
    
    public void setTOrderDetails(Set<TOrderDetail> tOrderDetails) {
        this.tOrderDetails = tOrderDetails;
    }




}


