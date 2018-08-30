package bookstore.vbean;

/**
 * TOrderDetail generated by hbm2java
 */
public class VOrderDetail  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

     private int id;
     private VBook vBook;
     private VOrder vOrder;

    public VOrderDetail() {
    }

    public VOrderDetail(int id, VBook vBook, VOrder vOrder) {
       this.id = id;
       this.vBook = vBook;
       this.vOrder = vOrder;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public VBook getVBook() {
        return this.vBook;
    }
    
    public void setVBook(VBook vBook) {
        this.vBook = vBook;
    }

    public VOrder getVOrder() {
        return this.vOrder;
    }
    
    public void setVOrder(VOrder vOrder) {
        this.vOrder = vOrder;
    }
}
