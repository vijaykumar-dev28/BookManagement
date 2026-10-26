package com.bookmanagement.dto;

public class BookResponseDTO {

	private Integer id;
	private String title;
	private String author;
	private Double price;
	private String genre;
	
	public BookResponseDTO(Integer id, String title, String author, Double price, String genre) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.price = price;
		this.genre = genre;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
	
}
