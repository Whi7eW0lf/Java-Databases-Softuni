package com.spring.spring.services.interfaces;

import com.spring.spring.entites.Author;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    void seedAuthors();

    long getAuthorsCount();

    Author findAuthorById(long id);

    List<Author> getAllAuthorWithBookBefore1990();

    List<Author> getAllAuthorOrderByBookCount();

    Author getAuthorByNames(String firstName,String lastName);
}
