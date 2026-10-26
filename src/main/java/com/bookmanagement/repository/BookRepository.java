package com.bookmanagement.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookmanagement.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
	boolean existsByTitle(String title);
	
	// Request Param
	List<Book> findByAuthor(String author);
	List<Book> findByGenre(String genre);
	List<Book> findByPriceLessThanEqual(Double price);
	List<Book> findByGenreAndPriceLessThanEqual(String genre,Double price);
	
	// Request Param with custom query
	
	@Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%',:keyword,'%'))")
	List<Book> searchByTitleKeyword(@Param("keyword") String keyword);
	
	@Query("SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice ORDER BY b.price ASC")
	List<Book> getBooksByPriceRange(@Param("minPrice") Double minPrice,@Param("maxPrice") Double maxPrice);
	
	@Query(value = "SELECT DISTINCT genre FROM book_table",nativeQuery = true)
	List<String> getAllGenres();
	
	@Query(value = "SELECT * FROM book_table ORDER BY price ASC LIMIT 5",nativeQuery = true)
	List<Book> getTop5CheapestBooks();
}
