package bookstore.pbean;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the T_BOOK database table.
 * 
 */
@Entity
@Table(name="T_BOOK")
@NamedQuery(name="TBook.findAll", query="SELECT t FROM TBook t")
public class TBook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(insertable=false, unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=32)
	private String author;

	@Column(nullable=false, length=32)
	private String isbn;

	@Column(nullable=false)
	private int price;

	@Column(nullable=false, length=32)
	private String publisher;

	@Column(nullable=false, length=128)
	private String title;

	public TBook() {
		// Do nothing.
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}