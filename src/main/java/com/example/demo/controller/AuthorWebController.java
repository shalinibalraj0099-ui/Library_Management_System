package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.services.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorWebController {

    private final AuthorService authorService;

    public AuthorWebController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String showAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("author", new Author());
        return "authors";
    }

    @PostMapping
    public String addAuthor(@ModelAttribute("author") Author author) {
        authorService.addAuthor(author);
        return "redirect:/authors";
    }

    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute("author") Author author) {
        authorService.addAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/edit/{id}")
    public String showEditAuthorForm(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id);
        if (author == null) {
            return "redirect:/authors";
        }
        model.addAttribute("author", author);
        return "edit-author";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id, @ModelAttribute("author") Author author) {
        authorService.updateAuthor(id, author);
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }
}
