package bookstore.service.ejb;

import javax.ejb.Remote;

import bookstore.service.CustomerService;

@Remote
public interface CustomerServiceRemote extends CustomerService {

}
