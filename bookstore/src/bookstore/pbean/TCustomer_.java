package bookstore.pbean;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-09-04T09:10:25.847+0900")
@StaticMetamodel(TCustomer.class)
public class TCustomer_ {
	public static volatile SingularAttribute<TCustomer, Integer> id;
	public static volatile SingularAttribute<TCustomer, String> username;
	public static volatile SingularAttribute<TCustomer, String> passwordmd5;
	public static volatile SingularAttribute<TCustomer, String> name;
	public static volatile SingularAttribute<TCustomer, String> email;
	public static volatile SetAttribute<TCustomer, TOrder> TOrders;
}
