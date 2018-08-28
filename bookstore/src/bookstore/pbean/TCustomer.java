package bookstore.pbean;
// Generated 2009/09/17 12:35:57 by Hibernate Tools 3.2.4.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TCustomer generated by hbm2java
 */
@Entity
@Table(name="T_CUSTOMER"
    ,schema="BOOKSTORE"
)
public class TCustomer  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

     private int id;
     private String username;
     private String passwordmd5;
     private String name;
     private String email;
     private Set<TOrder> TOrders = new HashSet<>(0);

    public TCustomer() {
    }

	
    public TCustomer(int id, String username, String passwordmd5, String name, String email) {
        this.id = id;
        this.username = username;
        this.passwordmd5 = passwordmd5;
        this.name = name;
        this.email = email;
    }
    public TCustomer(int id, String username, String passwordmd5, String name, String email, Set<TOrder> TOrders) {
       this.id = id;
       this.username = username;
       this.passwordmd5 = passwordmd5;
       this.name = name;
       this.email = email;
       this.TOrders = TOrders;
    }
   
     @Id 

    
    @Column(name="ID", nullable=false, precision=22, scale=0, insertable=false, updatable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    @Column(name="USERNAME", unique=true, nullable=false, length=256)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    
    @Column(name="PASSWORDMD5", nullable=false, length=256)
    public String getPasswordmd5() {
        return this.passwordmd5;
    }
    
    public void setPasswordmd5(String passwordmd5) {
        this.passwordmd5 = passwordmd5;
    }

    
    @Column(name="NAME", nullable=false, length=256)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="EMAIL", nullable=false, length=256)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="TCustomer")
    public Set<TOrder> getTOrders() {
        return this.TOrders;
    }
    
    public void setTOrders(Set<TOrder> TOrders) {
        this.TOrders = TOrders;
    }




}


