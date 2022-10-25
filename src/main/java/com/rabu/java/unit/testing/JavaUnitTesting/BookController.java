package com.rabu.java.unit.testing.JavaUnitTesting;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/book")
public class BookController {
	
	@Autowired
	BookRepository repo;
	
	@GetMapping
	public List<Book> getAllBooks() {
		return repo.findAll();
	}
	
	@GetMapping("{bookId}")
	public Book getBookById(@PathVariable(value = "bookId") Long bookId) {
		return repo.findById(bookId).get();
	}
	
	@PostMapping
	public Book CreateBookRecord(@RequestBody Book bookRecord) {
		return repo.save(bookRecord);
	}
	
	@PutMapping
	public Book UpdateBook(@RequestBody Book bookRecord) throws NotFoundException {
		if(bookRecord == null || bookRecord.getBookId() == null)
			throw new NotFoundException();
		Optional<Book> optionalBook = repo.findById(bookRecord.getBookId());
		if(!optionalBook.isPresent())
			throw new NotFoundException();
		
		Book existingBookRecord = optionalBook.get();
		existingBookRecord.setName(bookRecord.getName());
		existingBookRecord.setAuthor(bookRecord.getAuthor());
		existingBookRecord.setRating(bookRecord.getRating());
		
		return repo.save(existingBookRecord);
	}
	
	@DeleteMapping("{bookId}")
	public Book DeleteBook(@PathVariable(name="bookId") Long bookId) throws NotFoundException{
		Optional<Book> optionalBook = repo.findById(bookId);
		if(!optionalBook.isPresent())
			throw new NotFoundException();
		repo.deleteById(bookId);
		return optionalBook.get();
	}
	
}
