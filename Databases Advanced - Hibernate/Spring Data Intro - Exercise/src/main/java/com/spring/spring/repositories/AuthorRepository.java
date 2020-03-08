package com.spring.spring.repositories;

import com.spring.spring.entites.Author;
import com.spring.spring.entites.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author,Long> {

    @Query(value =
            "SELECT authors.id, authors.first_name, authors.last_name\n" +
                    "  FROM (SELECT a.id, a.first_name, a.last_name, count(b.id) AS count\n" +
                    "        FROM authors AS a\n" +
                    "          INNER JOIN books AS b\n" +
                    "            ON a.id = b.author_id\n" +
                    "           AND b.release_date < :date\n" +
                    "        GROUP BY a.id, a.first_name, a.last_name\n" +
                    "       HAVING count(b.id) > 0) AS authors\n" +
                    "ORDER BY authors.count DESC;\n", nativeQuery = true)
    List<Author> findAuthorsWithBooksReleasedBeforeDate(@Param("date") LocalDate date);

    @Query(value = "SELECT a FROM Author AS a ORDER BY a.books.size DESC")
    List<Author> getAuthorsOrderByBooksCount();

    Author getAuthorsByFirstNameAndLastName(String firstName,String lastName);

}
