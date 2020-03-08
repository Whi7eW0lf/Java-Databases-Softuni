package com.spring.spring.services.impl;

import com.spring.spring.constants.GlobalConstants;
import com.spring.spring.entites.Author;
import com.spring.spring.entites.Book;
import com.spring.spring.entites.Category;
import com.spring.spring.entites.enums.AgeRestriction;
import com.spring.spring.entites.enums.EditionType;
import com.spring.spring.repositories.BookRepository;
import com.spring.spring.services.interfaces.AuthorService;
import com.spring.spring.services.interfaces.BookService;
import com.spring.spring.services.interfaces.CategoryService;
import com.spring.spring.utill.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private FileUtil fileUtil;
    private BookRepository bookRepository;
    private AuthorService authorService;
    private CategoryService categoryService;

    @Autowired
    public BookServiceImpl(FileUtil fileUtil,BookRepository bookRepository,AuthorService authorService,CategoryService categoryService) {
        this.fileUtil = fileUtil;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() {

        Arrays.stream(this.fileUtil.readFileContent(GlobalConstants.BOOKS_PATH))
                .forEach(r-> {

                    String[] params = r.split("\\s+");

                    Author author = getRandomAuthor();
                    EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate releaseDate = LocalDate.parse(params[1],formatter);
                    int copies = Integer.parseInt(params[2]);
                    BigDecimal price = new BigDecimal(params[3]);
                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];
                    String title = getTitle(params);
                    Set<Category> categories = getRandomCategories();

                    Book book = new Book();

                    book.setAuthor(author);
                    book.setEditionType(editionType);
                    book.setReleaseDate(releaseDate);
                    book.setCopies(copies);
                    book.setPrice(price);
                    book.setAgeRestriction(ageRestriction);
                    book.setTitle(title);
                    book.setCategories(categories);
                    this.bookRepository.saveAndFlush(book);
                });


    }

    private Set<Category> getRandomCategories() {

        Set<Category> categories = new HashSet<>();

        Random random = new Random();

        int bound = random.nextInt(3)+1;

        for (int i = 1; i <=bound; i++) {
            categories.add(this.categoryService.getCategoryById(i));
        }

        return categories;
    }

    private String getTitle(String[] params) {

        StringBuilder builder = new StringBuilder();

        for (int i = 5; i < params.length; i++) {
            builder.append(params[i]).append(" ");
        }

        return builder.toString().trim();
    }

    private Author getRandomAuthor() {

        Random random = new Random();
        int randomId = random.nextInt((int) this.authorService.getAuthorsCount())+1;

        return this.authorService.findAuthorById(randomId);
    }
}
