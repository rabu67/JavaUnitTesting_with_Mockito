package com.rabu.java.unit.testing.JavaUnitTesting;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class BookControllerTest {
	
	private MockMvc mockMvc;
	
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = new ObjectMapper().writer();
	
	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookController bookController;
	
	Book record_1 = new Book(1L, "Java", "Rajbabu", 5);
	Book record_2 = new Book(2L, "Python", "Prasanna", 3);
	Book record_3 = new Book(3L, "ReactJS", "Arun", 4);
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}
	
	@Test
	public void getAllBooks_success() throws Exception {
		List<Book> records = new ArrayList<>(Arrays.asList(record_1,record_2,record_3));
		
		Mockito.when(bookRepository.findAll()).thenReturn(records);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/book")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[2].name", is("ReactJS")));
	
	}
	
	@Test
	public void getBookById_success() throws Exception {
		Mockito.when(bookRepository.findById(record_1.getBookId())).thenReturn(java.util.Optional.of(record_1));
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/book/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Java")));
	}
	
	@Test
	public void createBook_success() throws Exception {
		Book record = Book.builder()
						.bookId(4L)
						.name("Angular")
						.author("Siva")
						.rating(5).build();
		Mockito.when(bookRepository.save(record)).thenReturn(record);
		
		String content = objectWriter.writeValueAsString(record);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(content);
		
		mockMvc.perform(mockRequest)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", notNullValue()))
			.andExpect(jsonPath("$.name", is("Angular")));
	}
	
	@Test
	public void updateRecord_success() throws Exception {
		Book updatedRecord = Book.builder()
								.bookId(1L)
								.name("Updated name")
								.author("updated Author")
								.rating(5).build();
		
		Mockito.when(bookRepository.findById(record_1.getBookId())).thenReturn(java.util.Optional.of(record_1));
		Mockito.when(bookRepository.save(updatedRecord)).thenReturn(updatedRecord);
		
		String content = objectWriter.writeValueAsString(updatedRecord);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/book")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(content);
		
		mockMvc.perform(mockRequest)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is("Updated name")));
		
	}
	
	@Test
	public void deleteBook_success() throws Exception {
		Mockito.when(bookRepository.findById(record_1.getBookId())).thenReturn(java.util.Optional.of(record_1));
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/book/1")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk());
	}

	
}
