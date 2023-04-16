package com.magicofbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicofbook.dto.ResponseDto;
import com.magicofbook.entity.Book;
import com.magicofbook.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	BookService bookService;
	
	@GetMapping
	public ResponseEntity<ResponseDto> getAllBooks(){
		System.out.println("Inside Book Service");
		List<Book> books= bookService.getAllBooks();
		if(books!=null){
			books.forEach(b-> System.out.println(b));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success",books));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Can not fetch the List",books));
	}
	
	@GetMapping("/author/{author}")
	public ResponseEntity<ResponseDto> getABooksByAuthorName(@PathVariable String author){
		System.out.println("Inside Book Service");
		List<Book> books= bookService.getBooksByAuthorName(author);
		if(books!=null){
			books.forEach(b-> System.out.println(b));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success",books));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Can not fetch the List",books));
	}
	
	@GetMapping("/publication/{publication}")
	public ResponseEntity<ResponseDto> getABooksByPublicationnName(@PathVariable String publication){
		System.out.println("Inside Book Service");
		List<Book> books= bookService.getBooksPublicationName(publication);
		if(books!=null){
			books.forEach(b-> System.out.println(b));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success",books));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Can not fetch the List",books));
	}
	
	
	@GetMapping("bookTitle/{bookTitle}")
	public ResponseEntity<ResponseDto> getABookByBookTitle(@PathVariable String bookTitle){
		System.out.println("Inside Book Service");
		List<Book> books= bookService.getBooksByBookTitle(bookTitle);
		if(books!=null){
			books.forEach(b-> System.out.println(b));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success",books));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Can not fetch the List",books));
	}
	
	@GetMapping("/pricesorted/{startingPrice}/{endingPrice}")
	public ResponseEntity<ResponseDto> getABooksByPriceRange(@PathVariable Integer startingPrice, @PathVariable Integer endingPrice){
		System.out.println("Inside Book Service");
		List<Book> books= bookService.getBooksByPriceRange(startingPrice, endingPrice);
		if(books!=null){
			books.forEach(b-> System.out.println(b));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success",books));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Can not fetch the List",books));
	}
	
	@GetMapping("/PriceSorted")
	public ResponseEntity<ResponseDto> getAllBooksSortedByPriceAsc(){
		System.out.println("Inside Book Service");
		List<Book> books= bookService.getAllBooksSortedByPrice();
		if(books!=null){
			books.forEach(b-> System.out.println(b));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success",books));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Can not fetch the List",books));
	}
	
	@PostMapping
	public ResponseEntity<ResponseDto> insertBook(@RequestBody Book book, HttpSession session){
		System.out.println("Inside Book Controller Post Mapping");
		String email= (String) session.getAttribute("email");
		if(email!= null && email.endsWith("@admin.com")){
			Book insertedBook= bookService.insertBook(book);
			if(insertedBook!=null){
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(insertedBook.getBookTitle()+" Inserted",insertedBook));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(book.getBookTitle()+" can to be Inserted",insertedBook));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Access Denied- You are not logged in as an Admin",null));
		
	}
	
	@PutMapping
	public ResponseEntity<ResponseDto> updateBook(@RequestBody Book book, HttpSession session){
		System.out.println("Inside Book Controller Put Mapping");
		String email= (String) session.getAttribute("email");
		if(email!= null && email.endsWith("@admin.com")){
			Book updatedBook= bookService.updateBook(book);
			if(updatedBook!=null){
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseDto(updatedBook.getBookTitle()+" Updated",updatedBook));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(book.getBookTitle()+" can not be Updated",updatedBook));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Access Denied- You are not logged in as an Admin",null));
	}
	
	@DeleteMapping("/{bookId}")
	public ResponseEntity<ResponseDto> deleteBook(@PathVariable int bookId, HttpSession session){
		System.out.println("Inside Book Controller Delete Mapping");
		String email= (String) session.getAttribute("email");
		if(email!= null && email.endsWith("@admin.com")){
			Book deletedBook= bookService.deleteBook(bookId);
			if(deletedBook!=null){
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseDto(deletedBook.getBookTitle()+" Deleted",deletedBook));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("No Records found with Book Id: ",deletedBook));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("Access Denied- You are not logged in as an Admin",null));
	}
	
	@GetMapping("/{bookId}")
	public ResponseEntity<ResponseDto> getBookById(@PathVariable int bookId){
		System.out.println("Inside Book Controller Get Mapping");
		Book book= bookService.getBooksByid(bookId);
		//System.out.println(book.toString());
		if(book!=null){
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Success",book));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("No Records found with Book Id: "+bookId,book));
	}
	

}
