package bookstore.service.ejb;

import javax.ejb.Local;

import bookstore.service.BookService;

@Local
public interface BookServiceLocal extends BookService {

}
