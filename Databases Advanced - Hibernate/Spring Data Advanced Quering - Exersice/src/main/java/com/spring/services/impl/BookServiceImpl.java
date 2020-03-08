package com.spring.services.impl;

import com.spring.entites.Author;
import com.spring.entites.Book;
import com.spring.entites.Category;
import com.spring.entites.enums.AgeRestriction;
import com.spring.entites.enums.EditionType;
import com.spring.fileReaders.interfaces.FileInputReader;
import com.spring.repositories.BookRepository;
import com.spring.services.interfaces.AuthorService;
import com.spring.services.interfaces.BookService;
import com.spring.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static com.spring.constants.FilePaths.BOOKS_PATH;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private FileInputReader reader;

    private BookRepository bookRepository;
    private AuthorService authorService;
    private CategoryService categoryService;

    @Autowired
    public BookServiceImpl(FileInputReader reader,BookRepository bookRepository,AuthorService authorService,CategoryService categoryService) {
        this.reader = reader;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() {

        this.reader.readFile(BOOKS_PATH)
                .forEach(r-> {

                    String[] params = r.split("\\s+");

                    Author author = this.authorService.getRandomAuthor();
                    EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate releaseDate = LocalDate.parse(params[1],formatter);
                    int copies = Integer.parseInt(params[2]);
                    BigDecimal price = new BigDecimal(params[3]);
                    AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];
                    String title = getTitle(params);
                    Set<Category> categories = this.categoryService.getRandomCategories();

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

    @Override
    public List<Book> getAllBooksByAgeRestriction(AgeRestriction ageRestriction) {
        return this.bookRepository.findAllByAgeRestriction(ageRestriction);
    }

    @Override
    public List<Book> getAllBooksByCopiesIsLessThanAndEditionType(int count, EditionType editionType) {
        return this.bookRepository.findAllByCopiesLessThanAndEditionType(count,editionType);
    }

    @Override
    public List<Book> getAllBooksInPriceLessThanAndGreaterThan(BigDecimal less, BigDecimal greater) {
        return this.bookRepository.findAllByPriceIsLessThanAndPriceGreaterThan(less,greater);
    }

    @Override
    public List<Book> getAllBooksWithReleaseDateNotBetween(LocalDate before, LocalDate after) {
        return this.bookRepository.findBooksByReleaseDateBeforeOrReleaseDateAfter(before,after);
    }

    @Override
    public List<Book> getAllBooksWithReleaseDateBefore(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateBefore(date);
    }

    @Override
    public List<Book> getAllBooksWithTitleContainString(String string) {
        return this.bookRepository.findAllByTitleContains(string);
    }

    @Override
    public List<Book> getAllBooksWithAuthorLastNameStartWith(String string) {
        return this.bookRepository.findAllByAuthorLastNameStartWith(string);
    }

    @Override
    public List<Book> getAllBooksTitleLengthIsMoreGivenNumber(int number) {
        return this.bookRepository.findBooksTitleLengthIsMoreGivenNumber(number);
    }

    @Override
    public int getCountOfBookCopiesByAuthor(String fullName) {
        return this.bookRepository.findTotalNumbersOfBookCopiesByAuthor(fullName);
    }

    @Override
    public String getBookSelectedInformationByGivenTitle(String title) {
        Book book = this.bookRepository.findBookByTitle(title);
        return String.format("%s %s %s %.2f",book.getTitle(),book.getEditionType(),book.getAgeRestriction(),book.getPrice());
    }

    @Override
    public int updateBooksCopiesWithGivenDateAndCopies(LocalDate date, int copies) {
        return this.bookRepository.updateAllBooksAfterGivenDate(date,copies);
    }


    private String getTitle(String[] params) {

        StringBuilder builder = new StringBuilder();

        for (int i = 5; i < params.length; i++) {
            builder.append(params[i]).append(" ");
        }

        return builder.toString().trim();
    }


}