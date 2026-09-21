package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.MemberRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;

    public BookService(BookRepository bookRepository,AuthorRepository authorRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.memberRepository = memberRepository;
    }
    // Add a new book
    public Book addBook(Book book) {
    Author author = authorRepository
            .findById(book.getAuthor().getId())
            .orElse(null);

    book.setAuthor(author);

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

    Author author = authorRepository
            .findById(updatedBook.getAuthor().getId())
            .orElse(null);

    existingBook.setAuthor(author);

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