package com.spring.spring.repositories;

import com.spring.spring.entites.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
