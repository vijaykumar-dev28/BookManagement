package com.bookmanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bookmanagement.dto.BookRequestDTO;
import com.bookmanagement.dto.BookResponseDTO;
import com.bookmanagement.entity.Book;
import com.bookmanagement.exceptions.BookNotFoundException;
import com.bookmanagement.exceptions.DuplicateBookException;
import com.bookmanagement.exceptions.InvalidBookException;
import com.bookmanagement.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository repo;
	
	// helper method to convert entity to dto
	
	private BookResponseDTO toResponseDTO(Book b) {
		return new BookResponseDTO(b.getId(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getGenre());
	}
	
	private List<BookResponseDTO> toListResponseDTO(List<Book> book){
		List<BookResponseDTO> bookResponse=book.stream().map(this::toResponseDTO).collect(Collectors.toList());
		return bookResponse;
	}
	
	private Book toEntity(BookRequestDTO dto) {
		Book b=new Book();
		b.setTitle(dto.getTitle());
		b.setAuthor(dto.getAuthor());
		b.setPrice(dto.getPrice());
		b.setGenre(dto.getGenre());
		return b;
	}
	
	// service methods
	
	public void addBook(BookRequestDTO dto) {
		if(repo.existsByTitle(dto.getTitle())) {
			throw new DuplicateBookException("Book already exists");
		}
		repo.save(toEntity(dto));
	}
	
	public void addAllBook(List<BookRequestDTO> books) {
		
		for (BookRequestDTO bookRequestDTO : books) {
			if(repo.existsByTitle(bookRequestDTO.getTitle())) {
				throw new DuplicateBookException(bookRequestDTO.getTitle()+" book already exists");
			}
		}
		List<Book> booksEntity=books.stream().map(this::toEntity).collect(Collectors.toList());
		
		repo.saveAll(booksEntity);
	}
	
	public BookResponseDTO getBookById(Integer id) {
		if(id<=0) {
			throw new InvalidBookException("Id can't be zero or below");
		}
		Book b=repo.findById(id).orElseThrow(()->new BookNotFoundException("Book not exists"));
		return toResponseDTO(b);
	}
	
	public List<BookResponseDTO> getAllBooks(){
		return toListResponseDTO(repo.findAll());
	}
	
	public void updateBook(Integer id,BookRequestDTO updatedBook) {
		if(id<=0) {
			throw new InvalidBookException("Id can't be zero or below");
		}
		Book exists=repo.findById(id).orElseThrow(()->new BookNotFoundException("Book not found"));
		exists.setTitle(updatedBook.getTitle());
		exists.setAuthor(updatedBook.getAuthor());
		exists.setPrice(updatedBook.getPrice());
		exists.setGenre(updatedBook.getGenre());
		repo.save(exists);
	}
	
	public void deleteBookByid(Integer id) {
		if(id<=0) {
			throw new InvalidBookException("Id can't be zero or below");
		}
		Book exists=repo.findById(id).orElseThrow(()->new BookNotFoundException("Book not found"));
		repo.delete(exists);
	}
	
	// custom filters
	
	public List<BookResponseDTO> getBooksByAuthor(String author){
		List<Book> books=repo.findByAuthor(author);
		if(books.isEmpty()) {
			throw new BookNotFoundException("No books found for author :"+author);
		}
		return toListResponseDTO(books);
	}
	
	public List<BookResponseDTO> filterBooks(String genre,Double maxPrice) {
		if(genre!=null && maxPrice!=null) {
			List<Book> books=repo.findByGenreAndPriceLessThanEqual(genre, maxPrice);
			return toListResponseDTO(books);
		}else if(genre!=null) {
			List<Book> books=repo.findByGenre(genre);
			return toListResponseDTO(books);
		}else if(maxPrice!=null) {
			List<Book> books=repo.findByPriceLessThanEqual(maxPrice);
			return toListResponseDTO(books);
		}else {
			return toListResponseDTO(repo.findAll());
		}
	}
	
	// for custom queries
	
	public List<BookResponseDTO> searchByTitleKeyword(String keyword){
		List<Book> books=repo.searchByTitleKeyword(keyword);
		if(books.isEmpty()) {
			throw new BookNotFoundException("Book not found with keyword :"+keyword);
		}
		return toListResponseDTO(books);
	}
	
	public List<BookResponseDTO> getBooksByPriceRange(Double minPrice,Double maxPrice){
		if(minPrice<0||maxPrice<0) {
			throw new InvalidBookException("Price can't be negative");
		}
		if(minPrice>maxPrice) {
			throw new InvalidBookException("Minimum price can't be greater than Maximum price");
		}
		
		List<Book> books=repo.getBooksByPriceRange(minPrice, maxPrice);
		if(books.isEmpty()) {
			throw new BookNotFoundException("No book found in this price range");
		}
		return toListResponseDTO(books);
	}
	
	public List<String> getAllGenres(){
		List<String> genres=repo.getAllGenres();
		return genres;
	}
	
	public List<BookResponseDTO> getTop5CheapestBooks(){
		List<Book> books=repo.getTop5CheapestBooks();
		return toListResponseDTO(books);
	}
	
	// paginated
	
	public Page<BookResponseDTO> getAllBooksPaginated(int page,int size,String sortBy, String direction){
		Sort sort=direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		
		Pageable pageable=PageRequest.of(page, size, sort);
		Page<Book> books=repo.findAll(pageable);
		return books.map(this::toResponseDTO);
	}
}
