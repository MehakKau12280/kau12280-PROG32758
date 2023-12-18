package ca.sheridancollege.kau12280.beans;


import java.util.List;

import lombok.Data;

@Data
public class Book {
	private long id;
	private String title;
	private String author;
	
	private List<Review> reviews;
}
