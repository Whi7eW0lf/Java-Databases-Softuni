package com.spring.spring.controllers;

import com.spring.spring.entites.Author;
import com.spring.spring.entites.Book;
import com.spring.spring.services.interfaces.AuthorService;
import com.spring.spring.services.interfaces.BookService;
import com.spring.spring.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class AppController implements CommandLineRunner {

    private CategoryService categoryService;
    private AuthorService authorService;
    private BookService bookService;

    @Autowired
    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {

//        Seed Data into the Database
//        seedData();

//        1. Get all books after the year 2000. Print only their titles.

//        this.bookService.getAllBooksAfter2000().forEach(e-> System.out.println(String.format("%s",e.getTitle())));

//        2. Get all authors with at least one book with release date before 1990. Print their first name and last name.

//        this.authorService.getAllAuthorWithBookBefore1990().forEach(e-> System.out.println(String.format("%s %s",e.getFirstName(),e.getLastName())));

//        3.Get all authors, ordered by the number of their books (descending). Print their first name, last name and
//        book count.

//        this.authorService
//                .getAllAuthorOrderByBookCount()
//                .forEach(e-> System.out.println(String.format("%s %s %d",e.getFirstName(),e.getLastName(),e.getBooks().size())));

//        4.	Get all books from author George Powell, ordered by their release date (descending), then by book title (ascending).
//       Print the book's title, release date and copies.

//        this.bookService
//                .getAllBooksWithAuthorOrderByDesc(this.authorService.getAuthorByNames("George","Powell"))
//                .forEach(b-> System.out.println(String.format("%s %s %d",b.getTitle(),b.getReleaseDate(),b.getCopies())));

    }

    public void seedData() {
        this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
    }

}
