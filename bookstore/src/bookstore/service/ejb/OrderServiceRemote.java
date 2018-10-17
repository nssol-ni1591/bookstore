package bookstore.service.ejb;

import javax.ejb.Remote;

import bookstore.service.OrderService;

@Remote
public interface OrderServiceRemote extends OrderService {

}
