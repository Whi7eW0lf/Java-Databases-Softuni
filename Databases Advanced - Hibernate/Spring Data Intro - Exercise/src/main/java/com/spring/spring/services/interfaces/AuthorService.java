package com.spring.spring.services.interfaces;

import com.spring.spring.entites.Author;

public interface AuthorService {

    void seedAuthors();

    long getAuthorsCount();

    Author findAuthorById(long id);
}
