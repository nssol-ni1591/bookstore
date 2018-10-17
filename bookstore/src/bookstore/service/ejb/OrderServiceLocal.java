package bookstore.service.ejb;

import javax.ejb.Local;

import bookstore.service.OrderService;

@Local
public interface OrderServiceLocal extends OrderService {

}
