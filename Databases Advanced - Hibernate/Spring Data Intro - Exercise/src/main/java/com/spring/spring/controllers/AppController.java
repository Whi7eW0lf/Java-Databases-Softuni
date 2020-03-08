package com.spring.spring.controllers;

import com.spring.spring.entites.Book;
import com.spring.spring.services.impl.CategoryServiceImpl;
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
    public AppController(CategoryService categoryService,AuthorService authorService,BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {

//        this.authorService.seedAuthors();
//        this.categoryService.seedCategories();
//        this.bookService.seedBooks();

        //1. Get all books after the year 2000. Print only their titles.
        this.bookService.getAllBooksAfter2000().forEach(e-> System.out.println(String.format("%s",e.getTitle())));




    }

}
