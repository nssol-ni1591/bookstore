package bookstore.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bookstore.dao.OrderDAO;
import bookstore.pbean.TCustomer;
import bookstore.pbean.TOrder;

public class OrderDAOImpl extends HibernateDaoSupport
  implements OrderDAO{
	public TOrder createOrder(TCustomer inCustomer){

		TOrder saveOrder = new TOrder();
		saveOrder.setTCustomer( inCustomer );
		saveOrder.setOrderday( Calendar.getInstance().getTime() );
		
		getHibernateTemplate().save( saveOrder );
		
		return( saveOrder );
	}

  public List retrieveOrders(final List orderIdList) {
    //return new ArrayList();

    HibernateTemplate ht = getHibernateTemplate();

    if (orderIdList == null) {
      return(ht.find( "from TOrder order" ));
    }
    else {
      return(((List)ht.execute(new HibernateCallback() {
	  public Object doInHibernate(Session session)
	    throws HibernateException {
	    Query retrieveQuery =
	      session.createQuery("from TOrder order where order.id in ( :ID )");
	    retrieveQuery.setParameterList("ID", orderIdList);
	    return(retrieveQuery.list());
	  }
	} )));
    }
  }
}
