package bookstore.vbean;

import bookstore.pbean.TBook;

public class VBook{
	
	     private String isbn;
	     private String title;
	     private String author;
	     private String publisher;
	     private int price;
	     private boolean selected;

	    public VBook(){}
	    
	    public VBook( TBook book ){
	    	this.isbn = book.getIsbn();
	    	this.title = book.getTitle();
	    	this.author = book.getAuthor();
	    	this.publisher = book.getPublisher();
	    	this.price = book.getPrice();
	    	this.selected = false;
	    }
	    
	    public String getIsbn() {
	        return this.isbn;
	    }	    
	    public void setIsbn(String isbn) {
	        this.isbn = isbn;
	    }

	    public String getTitle() {
	        return this.title;
	    }
	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getAuthor() {
	        return this.author;
	    }
	    public void setAuthor(String author) {
	        this.author = author;
	    }

	    public String getPublisher() {
	        return this.publisher;
	    }
	    public void setPublisher(String publisher) {
	        this.publisher = publisher;
	    }

	    public int getPrice() {
	        return this.price;
	    }
	    public void setPrice(int price) {
	        this.price = price;
	    }

		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
}