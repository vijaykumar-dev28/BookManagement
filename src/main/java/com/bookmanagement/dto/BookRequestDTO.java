package com.bookmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequestDTO {
	
	@NotBlank(message = "Title can't be empty")
	private String title;
	@NotBlank(message = "Author can't be empty")
	private String author;
	@NotNull(message = "Price can't be empty")
	@Min(value = 0,message = "Price can't be negative")
	private Double price;
	@NotBlank(message = "Genre can't be empty")
	private String genre;
	
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
