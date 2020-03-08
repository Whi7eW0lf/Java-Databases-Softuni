package com.spring.spring.services.impl;

import com.spring.spring.entites.Author;
import com.spring.spring.repositories.AuthorRepository;
import com.spring.spring.services.interfaces.AuthorService;
import com.spring.spring.utill.FileUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.spring.spring.constants.GlobalConstants.AUTHORS_PATH;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;
    private FileUtil fileUtil;

    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() {
        String[] authors = this.fileUtil.readFileContent(AUTHORS_PATH);

        for (String author : authors) {
            String[] names = author.split("\\s+");
            this.authorRepository.saveAndFlush(new Author(names[0],names[1]));
        }

    }

    @Override
    public long getAuthorsCount() {
        return this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<Author> getAllAuthorWithBookBefore1990() {
        return this.authorRepository.findAuthorsWithBooksReleasedBeforeDate(LocalDate.of(1990,1,1));
    }

    @Override
    public List<Author> getAllAuthorOrderByBookCount() {
        return this.authorRepository.getAuthorsOrderByBooksCount();
    }

    @Override
    public Author getAuthorByNames(String firstName,String lastName) {
        return this.authorRepository.getAuthorsByFirstNameAndLastName(firstName,lastName);
    }


}
