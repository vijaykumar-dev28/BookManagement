package com.bookmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmanagement.dto.BookRequestDTO;
import com.bookmanagement.dto.BookResponseDTO;
import com.bookmanagement.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookService service;
	
	@PostMapping
	public ResponseEntity<String> addBook(@Valid @RequestBody BookRequestDTO b){
		service.addBook(b);
		return ResponseEntity.status(201).body("Book added");
	}
	
	@PostMapping("/bulk")
	public ResponseEntity<String> addAllBooks(@Valid @RequestBody List<BookRequestDTO> books){
		service.addAllBook(books);
		return ResponseEntity.status(201).body(books.size()+" books are added!");
	}
	
	@GetMapping
	public ResponseEntity<List<BookResponseDTO>> getAllBooks(){
		return ResponseEntity.ok(service.getAllBooks());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Integer id){
		BookResponseDTO b=service.getBookById(id);
		return ResponseEntity.ok(b);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateBook(@PathVariable Integer id,@Valid @RequestBody BookRequestDTO updatedBook){
		service.updateBook(id, updatedBook);
		return ResponseEntity.ok("Book updated!");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBookById(@PathVariable Integer id){
		service.deleteBookByid(id);
		return ResponseEntity.noContent().build();
	}
	
	
	// custom get
	
	@GetMapping("/search")
	public ResponseEntity<List<BookResponseDTO>> searchBooksByAuthor(@RequestParam String author){
		List<BookResponseDTO> books=service.getBooksByAuthor(author);
		return ResponseEntity.ok(books);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<List<BookResponseDTO>> filterBooks(@RequestParam(required = false) String genre,@RequestParam(required = false) Double maxPrice){
		return ResponseEntity.ok(service.filterBooks(genre, maxPrice));
	}
	
	@GetMapping("/search/title")
	public ResponseEntity<List<BookResponseDTO>> searchByTitleKeyword(@RequestParam String keyword) {
	    return ResponseEntity.ok(service.searchByTitleKeyword(keyword));
	}

	@GetMapping("/price-range")
	public ResponseEntity<List<BookResponseDTO>> getByPriceRange(
	        @RequestParam Double minPrice,
	        @RequestParam Double maxPrice) {
	    return ResponseEntity.ok(service.getBooksByPriceRange(minPrice, maxPrice));
	}

	@GetMapping("/genres")
	public ResponseEntity<List<String>> getAllGenres() {
	    return ResponseEntity.ok(service.getAllGenres());
	}

	@GetMapping("/top5-cheapest")
	public ResponseEntity<List<BookResponseDTO>> getTop5CheapestBooks() {
	    return ResponseEntity.ok(service.getTop5CheapestBooks());
	}
	
	// pagination
	
	@GetMapping("/paginated")
	public ResponseEntity<Page<BookResponseDTO>> getAllBooksPaginated(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size,@RequestParam(defaultValue = "id") String sortBy,@RequestParam(defaultValue = "asc") String direction){
		return ResponseEntity.ok(service.getAllBooksPaginated(page, size, sortBy,direction));
	}
}
