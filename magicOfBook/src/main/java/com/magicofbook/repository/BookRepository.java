package com.magicofbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.repository.query.Param;

import com.magicofbook.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	
	public List<Book> findByAuthorName(String authorName);
	
	public List<Book> findByPublicationName(String publicationName);
	
	public List<Book> findByBookTitle(String bookTitle);
	
	@Query("Select b from Book b where b.price >=:startingPrice and b.price <=:endingPrice")
	public List<Book> findByPriceRange(@Param("startingPrice") int startingPrice, @Param("endingPrice") int endingPrice);
	
	@Query("Select b from Book b order by b.price")
	public List<Book> findAllBooksSortedByPrice();

}
