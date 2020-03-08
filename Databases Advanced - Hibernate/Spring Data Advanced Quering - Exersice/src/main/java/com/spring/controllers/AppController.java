package com.spring.controllers;

import com.spring.entites.Book;
import com.spring.entites.enums.AgeRestriction;
import com.spring.entites.enums.EditionType;
import com.spring.services.interfaces.AuthorService;
import com.spring.services.interfaces.BookService;
import com.spring.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import javax.print.attribute.standard.Copies;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Scanner;

@Controller
public class AppController implements CommandLineRunner {

    private AuthorService authorService;
    private BookService bookService;
    private CategoryService categoryService;

    private Scanner reader;

    @Autowired
    public AppController(AuthorService authorService, BookService bookService, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.reader = new Scanner(System.in);
    }

    @Override
    public void run(String... args) {

//        Seed data in database
//        seedDatabase();

//       1. Books Titles by Age Restriction
//        bookTitlesByAgeRestriction();

//       2. Golden Books
//        goldenEditionBooks();

//       3. Books by Price
//        booksByPrice();

//       4.Not Released Books
//        notReleasedBooks();

//       5.Books Released Before Date
//        booksReleasedBeforeDate();

//       6.Authors Search
//        authorSearch();

//       7.Books Search
//        booksSearch();

//       8.Book Titles Search
//        bookTitleSearch();

//       9.Count Books
//        countBooks();

//       10.Total Book Copies
//        totalBookCopies();

//       11.Reduced Book
//        reducedBook();

//       12.Increase Book Copies
//        increaseBooksCopies();
    }

    private void increaseBooksCopies() {
        System.out.println("Please enter date in format : dd MMM yyyy");

        String date = this.reader.nextLine();

        System.out.println("Please enter copies");

        int copies = this.reader.nextInt();

        System.out.println();

        LocalDate parseDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM YYYY"));

        System.out.println(this.bookService.updateBooksCopiesWithGivenDateAndCopies(parseDate, copies) * copies);
    }

    private void reducedBook() {
        System.out.println("Please enter book title:");

        System.out.println(this.bookService.getBookSelectedInformationByGivenTitle(this.reader.nextLine()));
    }

    private void totalBookCopies() {
        System.out.println("Please enter author full name separated by space:");

        String authorName = this.reader.nextLine();

        int totalCopies = this.bookService.getCountOfBookCopiesByAuthor(authorName);

        System.out.println(String.format("%s - %d", authorName, totalCopies));
    }

    private void countBooks() {

        System.out.println("Please enter number:");

        System.out.println(this.bookService.getAllBooksTitleLengthIsMoreGivenNumber(this.reader.nextInt()).size());
    }

    private void bookTitleSearch() {
        System.out.println("Please enter string:");

        String string = this.reader.nextLine();

        this.bookService.getAllBooksWithAuthorLastNameStartWith(string)

                .forEach(e -> System.out.println(String.format("%s (%s %s)"
                        , e.getTitle()
                        , e.getAuthor().getFirstName()
                        , e.getAuthor().getLastName())));
    }

    private void booksSearch() {

        System.out.println("Please enter string:");

        String string = this.reader.nextLine().toLowerCase();

        this.bookService.getAllBooksWithTitleContainString(string)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void authorSearch() {

        System.out.println("Please enter string:");

        String string = this.reader.nextLine();

        this.authorService
                .getAllAuthorsWithFirstNameEndWith(string).forEach(e -> System.out.printf("%s %s%n", e.getFirstName(), e.getLastName()));


    }

    private void booksReleasedBeforeDate() {

        System.out.println("Please enter date with format \"dd-mm-yyyy\":");

        String date = this.reader.nextLine();

        LocalDate beforeDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        this.bookService.getAllBooksWithReleaseDateBefore(beforeDate)
                .forEach(e -> System.out.printf("%s %s %.2f%n", e.getTitle(), e.getEditionType(), e.getPrice()));
    }

    private void notReleasedBooks() {
        System.out.println("Please enter year:");
        int year = Integer.parseInt(this.reader.nextLine());

        this.bookService.getAllBooksWithReleaseDateNotBetween(LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31))
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void booksByPrice() {
        this.bookService
                .getAllBooksInPriceLessThanAndGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(e -> System.out.println(String.format("%s - $%.2f", e.getTitle(), e.getPrice())));
    }

    private void goldenEditionBooks() {
        this.bookService
                .getAllBooksByCopiesIsLessThanAndEditionType(5000, EditionType.GOLD)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void bookTitlesByAgeRestriction() {

        System.out.println("Please enter age restriction:");

        String input = this.reader.nextLine().toUpperCase();

        AgeRestriction ageRestriction;

        try {
            ageRestriction = AgeRestriction.valueOf(input);
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid age restriction");
            return;
        }

        this.bookService
                .getAllBooksByAgeRestriction(ageRestriction)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);

    }

    private void seedDatabase() {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }

}
