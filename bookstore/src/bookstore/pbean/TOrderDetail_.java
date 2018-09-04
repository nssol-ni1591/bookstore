package bookstore.pbean;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-09-04T09:10:25.859+0900")
@StaticMetamodel(TOrderDetail.class)
public class TOrderDetail_ {
	public static volatile SingularAttribute<TOrderDetail, Integer> id;
	public static volatile SingularAttribute<TOrderDetail, TBook> TBook;
	public static volatile SingularAttribute<TOrderDetail, TOrder> TOrder;
}
