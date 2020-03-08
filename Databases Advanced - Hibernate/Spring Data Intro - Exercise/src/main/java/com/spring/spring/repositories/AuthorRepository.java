package com.spring.spring.repositories;

import com.spring.spring.entites.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
