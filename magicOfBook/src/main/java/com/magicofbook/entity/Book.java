package com.magicofbook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="book_table")
public class Book {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="book_id")
	private int bookId;
	@Column(name="book_title")
	private String bookTitle;
	@Column(name="book_genres")
	private String bookGenres;
	@Column(name="author_name")
	private String authorName;
	@Column(name="publication_name")
	private String publicationName;
	@Column(name="book_price")
	private int price;
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Book(String bookTitle, String bookGenres, String authorName, String publicationName, int price) {
		super();
		this.bookTitle = bookTitle;
		this.bookGenres = bookGenres;
		this.authorName = authorName;
		this.publicationName = publicationName;
		this.price = price;
	}
	

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getPublicationName() {
		return publicationName;
	}

	public void setPublicationName(String publicationName) {
		this.publicationName = publicationName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getBookGenres() {
		return bookGenres;
	}
	public void setBookGenres(String bookGenres) {
		this.bookGenres = bookGenres;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookGenres=" + bookGenres + ", authorName="
				+ authorName + ", publicationName=" + publicationName + ", price=" + price + "]";
	}
	
	
	
	
	

}
