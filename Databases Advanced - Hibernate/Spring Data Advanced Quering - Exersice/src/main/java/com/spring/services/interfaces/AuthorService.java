package com.spring.services.interfaces;

import com.spring.entites.Author;

import java.util.List;

public interface AuthorService {
    void seedAuthors();

    Author getRandomAuthor();

    List<Author> getAllAuthorsWithFirstNameEndWith(String string);
}
