package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.services.AuthorService;
import com.example.demo.services.BookService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookWebController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookWebController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // Show all books
    @GetMapping
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("totalBooks", bookService.getAllBooks().size());
        model.addAttribute("totalAuthors", authorService.getAllAuthors().size());
        return "books";
    }

    // Show Add Book page
    @GetMapping("/new")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "add-book";
    }

    // Save Book
    @PostMapping("/save")
    public String saveBook(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            @RequestParam(required = false) Long authorId,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            return "add-book";
        }

        if (authorId != null && authorId > 0) {
            Author author = authorService.getAuthorById(authorId);
            book.setAuthor(author);
        } else {
            book.setAuthor(null);
        }

        bookService.addBook(book);
        return "redirect:/books";
    }

    // Show Edit Book page
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "edit-book";
    }

    // Update Book
    @PostMapping("/update/{id}")
    public String updateBook(
            @PathVariable Long id,
            @Valid @ModelAttribute("book") Book book,
            BindingResult result,
            @RequestParam(required = false) Long authorId,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            return "edit-book";
        }

        if (authorId != null && authorId > 0) {
            Author author = authorService.getAuthorById(authorId);
            book.setAuthor(author);
        } else {
            book.setAuthor(null);
        }

        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    // Delete Book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}