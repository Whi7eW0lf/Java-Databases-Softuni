package com.spring.repositories;

import com.spring.entites.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    Author findAuthorByFirstNameAndLastName(String firstName,String lastName);

    List<Author> findAllAuthorsByFirstNameEndsWith(String string);
}
