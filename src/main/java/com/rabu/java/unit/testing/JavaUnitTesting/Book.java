package com.rabu.java.unit.testing.JavaUnitTesting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "book_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bookId;
	
	@NonNull
	private String name;
	
	@NonNull
	private String author;
	
	private int rating;
	
}
