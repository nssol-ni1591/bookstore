package bookstore.service.ejb;

import javax.ejb.Remote;

import bookstore.service.BookService;

@Remote
public interface BookServiceRemote extends BookService {

}
