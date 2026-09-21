package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> getAuthors() {
        return getAllAuthors();
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {

        Author existingAuthor =
                authorRepository.findById(id).orElse(null);

        if (existingAuthor == null) {
            return null;
        }

        existingAuthor.setName(updatedAuthor.getName());
        existingAuthor.setEmail(updatedAuthor.getEmail());

        return authorRepository.save(existingAuthor);
    }

    public String deleteAuthor(Long id) {

        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return "Author deleted successfully";
        }

        return "Author not found";
    }
}