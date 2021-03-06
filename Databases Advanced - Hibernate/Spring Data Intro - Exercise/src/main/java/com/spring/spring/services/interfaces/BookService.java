package com.spring.spring.services.interfaces;

import com.spring.spring.entites.Author;
import com.spring.spring.entites.Book;

import java.util.List;

public interface BookService {
    void seedBooks();

    List<Book> getAllBooksAfter2000();

    List<Book> getAllBooksWithAuthorOrderByDesc(Author author);
}
