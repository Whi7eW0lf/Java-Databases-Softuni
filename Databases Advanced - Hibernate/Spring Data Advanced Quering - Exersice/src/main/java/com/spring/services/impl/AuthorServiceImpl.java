package com.spring.services.impl;

import com.spring.entites.Author;
import com.spring.fileReaders.interfaces.FileInputReader;
import com.spring.repositories.AuthorRepository;
import com.spring.services.interfaces.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static com.spring.constants.FilePaths.AUTHOR_PATH;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;
    private FileInputReader reader;

    public AuthorServiceImpl(AuthorRepository authorRepository,FileInputReader reader) {
        this.authorRepository = authorRepository;
        this.reader = reader;
    }

    @Override
    public void seedAuthors() {

        this.reader.readFile(AUTHOR_PATH).forEach(e->{

            String[] names = e.split("\\s+");

            Author author = this.authorRepository.findAuthorByFirstNameAndLastName(names[0], names[1]);

            if (author==null){
                this.authorRepository.saveAndFlush(new Author(names[0],names[1]));
            }

        });
    }

    @Override
    public Author getRandomAuthor() {

        Random random = new Random();
        int randomId = random.nextInt((int) this.authorRepository.count())+1;

        return this.authorRepository.findById((long)randomId).orElse(null);
    }

    @Override
    public List<Author> getAllAuthorsWithFirstNameEndWith(String string) {
        return this.authorRepository.findAllAuthorsByFirstNameEndsWith(string);
    }
}
