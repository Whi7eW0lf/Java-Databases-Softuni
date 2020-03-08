package com.spring.services.interfaces;

import com.spring.entites.Book;
import com.spring.entites.enums.AgeRestriction;
import com.spring.entites.enums.EditionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks();

    List<Book> getAllBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> getAllBooksByCopiesIsLessThanAndEditionType(int count, EditionType editionType);

    List<Book> getAllBooksInPriceLessThanAndGreaterThan(BigDecimal less, BigDecimal greater);

    List<Book> getAllBooksWithReleaseDateNotBetween(LocalDate before, LocalDate after);

    List<Book> getAllBooksWithReleaseDateBefore(LocalDate date);

    List<Book> getAllBooksWithTitleContainString (String string);

    List<Book> getAllBooksWithAuthorLastNameStartWith (String string);

    List<Book> getAllBooksTitleLengthIsMoreGivenNumber(int number);

    int getCountOfBookCopiesByAuthor(String fullName);

    String getBookSelectedInformationByGivenTitle(String title);

    int updateBooksCopiesWithGivenDateAndCopies(LocalDate date , int copies);
}
