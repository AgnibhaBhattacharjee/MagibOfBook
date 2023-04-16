package com.magicofbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magicofbook.entity.Book;
import com.magicofbook.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	BookRepository bookRepo;
	
	public List<Book> getAllBooks(){
		System.out.println("Inside Book Service");
		return bookRepo.findAll();
	}
	
	public List<Book> getBooksByAuthorName(String authorName){
		return bookRepo.findByAuthorName(authorName);
	}
	
	public List<Book> getBooksPublicationName(String publicationName){
		return bookRepo.findByPublicationName(publicationName);
	}
	public List<Book> getBooksByBookTitle(String bookTitle){
		return bookRepo.findByBookTitle(bookTitle);
	}
	public Book getBooksByid(Integer bookId){
		System.out.println("Inside Book Service");
		if(bookRepo.existsById(bookId)){
			System.out.println(bookRepo.findById(bookId).get());
			return bookRepo.findById(bookId).get();
		}
		return null;
	
	}
	
	public List<Book> getBooksByPriceRange(int startingPrice, int endingPrice){
		return bookRepo.findByPriceRange(startingPrice, endingPrice);
		
	}
	
	public List<Book> getAllBooksSortedByPrice(){
		return bookRepo.findAllBooksSortedByPrice();
		
	}
	

	public Book insertBook(Book book) {
		// TODO Auto-generated method stub
		System.out.println("Inside Book Service");
		return bookRepo.save(book);
		
	}

	public Book updateBook(Book book) {
		System.out.println("Inside Book Service");
		// TODO Auto-generated method stub
		if(bookRepo.existsById(book.getBookId())){
			Book previousBook= bookRepo.findById(book.getBookId()).get();
			previousBook.setBookTitle(book.getBookTitle());
			previousBook.setAuthorName(book.getAuthorName());
			previousBook.setBookGenres(book.getBookGenres());
			previousBook.setPublicationName(book.getPublicationName());
			previousBook.setPrice(book.getPrice());
			bookRepo.save(previousBook);
			return previousBook;
			
		}else{
			return null;
		}
		
		
	}

	public Book deleteBook(Integer bookId) {
		System.out.println("Inside Book Service");
		// TODO Auto-generated method stub
		if(bookRepo.existsById(bookId)){
			Book book= bookRepo.findById(bookId).get();
			bookRepo.deleteById(bookId);
			return book;
		}
		else{
			return null;
		}
	}
	

}
