package com.spring.spring.repositories;

import com.spring.spring.entites.Author;
import com.spring.spring.entites.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate realeseDate);

    List<Book> getBooksByAuthorOrderByReleaseDateDescTitleAsc(Author author);
}
