package bookstore.service.ejb;

import javax.ejb.Local;

import bookstore.service.CustomerService;

@Local
public interface CustomerServiceLocal extends CustomerService {

}
