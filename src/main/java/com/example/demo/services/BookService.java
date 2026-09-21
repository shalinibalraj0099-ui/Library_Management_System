package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // Add a new book
    public Book addBook(Book book) {
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            Author author = authorRepository
                    .findById(book.getAuthor().getId())
                    .orElse(null);
            book.setAuthor(author);
        } else {
            book.setAuthor(null);
        }

        return bookRepository.save(book);
    }

    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooks() {
        return getAllBooks();
    }

    // Get book by ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    // Update book
    public Book updateBook(Long id, Book updatedBook) {
        Book existingBook = bookRepository.findById(id).orElse(null);

        if (existingBook == null) {
            return null;
        }

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setPrice(updatedBook.getPrice());

        if (updatedBook.getAuthor() != null && updatedBook.getAuthor().getId() != null) {
            Author author = authorRepository
                    .findById(updatedBook.getAuthor().getId())
                    .orElse(null);
            existingBook.setAuthor(author);
        } else {
            existingBook.setAuthor(null);
        }

        return bookRepository.save(existingBook);
    }

    // Delete book
    public String deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return "Book deleted successfully";
        }

        return "Book not found";
    }
}