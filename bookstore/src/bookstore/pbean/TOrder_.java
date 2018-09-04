package bookstore.pbean;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-09-04T09:10:25.853+0900")
@StaticMetamodel(TOrder.class)
public class TOrder_ {
	public static volatile SingularAttribute<TOrder, Integer> id;
	public static volatile SingularAttribute<TOrder, TCustomer> TCustomer;
	public static volatile SingularAttribute<TOrder, Date> orderday;
	public static volatile SetAttribute<TOrder, TOrderDetail> TOrderDetails;
}
