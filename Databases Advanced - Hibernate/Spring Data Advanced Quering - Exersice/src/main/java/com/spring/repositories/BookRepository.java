package com.spring.repositories;

import com.spring.entites.Author;
import com.spring.entites.Book;
import com.spring.entites.enums.AgeRestriction;
import com.spring.entites.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByCopiesLessThanAndEditionType(int count, EditionType editionType);

    @Query(value = "SELECT e FROM Book AS e WHERE e.price < :less OR e.price > :greater")
    List<Book> findAllByPriceIsLessThanAndPriceGreaterThan(@Param(value = "less") BigDecimal less, @Param(value = "greater") BigDecimal greater);

    List<Book> findBooksByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByTitleContains(String string);

    @Query("SELECT b FROM Book AS b WHERE b.author.lastName LIKE :string%")
    List<Book> findAllByAuthorLastNameStartWith(@Param("string") String string);

    @Query("SELECT b FROM Book AS b WHERE LENGTH(b.title)> :number")
    List<Book> findBooksTitleLengthIsMoreGivenNumber(@Param("number") int number);

    @Query("SELECT sum(b.copies) FROM Book as b WHERE concat(b.author.firstName,' ', b.author.lastName ) = :fullName")
    int findTotalNumbersOfBookCopiesByAuthor(@Param("fullName") String fullName);

    Book findBookByTitle(String title);

    @Modifying
    @Query("UPDATE Book AS b SET b.copies = b.copies+:copies WHERE b.releaseDate > :date")
    int updateAllBooksAfterGivenDate(@Param("date") LocalDate date , @Param("copies") int copies);
}
