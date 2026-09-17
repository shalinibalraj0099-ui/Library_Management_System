package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldPersistAndFetchBook() {
		Author author = authorRepository.save(new Author("Craig Walls", "craig@example.com"));
		Book book = new Book("Spring Boot in Action", 39.99, author);
		Book savedBook = bookRepository.save(book);

		assertNotNull(savedBook.getId());
		assertEquals("Spring Boot in Action", bookRepository.findById(savedBook.getId()).orElseThrow().getTitle());
	}

}
