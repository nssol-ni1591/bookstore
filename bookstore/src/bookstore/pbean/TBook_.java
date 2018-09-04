package bookstore.pbean;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2018-09-04T09:10:25.797+0900")
@StaticMetamodel(TBook.class)
public class TBook_ {
	public static volatile SingularAttribute<TBook, Integer> id;
	public static volatile SingularAttribute<TBook, String> isbn;
	public static volatile SingularAttribute<TBook, String> title;
	public static volatile SingularAttribute<TBook, String> author;
	public static volatile SingularAttribute<TBook, String> publisher;
	public static volatile SingularAttribute<TBook, Integer> price;
	public static volatile SetAttribute<TBook, TOrderDetail> TOrderDetails;
}
