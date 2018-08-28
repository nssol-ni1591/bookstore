package bookstore.pbean;
// Generated 2009/09/17 12:35:57 by Hibernate Tools 3.2.4.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TOrderDetail generated by hbm2java
 */
@Entity
@Table(name="T_ORDER_DETAIL"
    ,schema="BOOKSTORE"
)
public class TOrderDetail  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

     private int id;
     private TBook TBook;
     private TOrder TOrder;

    public TOrderDetail() {
    }

    public TOrderDetail(int id, TBook TBook, TOrder TOrder) {
       this.id = id;
       this.TBook = TBook;
       this.TOrder = TOrder;
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
    @JoinColumn(name="BOOK_ID_FK", nullable=false)
    public TBook getTBook() {
        return this.TBook;
    }
    
    public void setTBook(TBook TBook) {
        this.TBook = TBook;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ORDER_ID_FK", nullable=false)
    public TOrder getTOrder() {
        return this.TOrder;
    }
    
    public void setTOrder(TOrder TOrder) {
        this.TOrder = TOrder;
    }




}


