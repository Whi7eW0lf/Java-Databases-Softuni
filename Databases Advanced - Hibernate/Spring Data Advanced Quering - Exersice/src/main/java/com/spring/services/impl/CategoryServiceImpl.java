package com.spring.services.impl;

import com.spring.entites.Category;
import com.spring.fileReaders.interfaces.FileInputReader;
import com.spring.repositories.CategoryRepository;
import com.spring.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.spring.constants.FilePaths.CATEGORIES_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private FileInputReader reader;
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(FileInputReader reader, CategoryRepository categoryRepository) {
        this.reader = reader;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() {
        this.reader.readFile(CATEGORIES_PATH).forEach(n->{

            Category category = this.categoryRepository.findCategoryByName(n);

            if (category==null){
                this.categoryRepository.saveAndFlush(new Category(n));
            }

        });
    }

    @Override
    public Set<Category> getRandomCategories() {

        Set<Category> categories = new HashSet<>();

        Random random = new Random();

        int bound = random.nextInt(3)+1;

        for (int i = 1; i <=bound; i++) {
            categories.add(this.categoryRepository.getOne((long)i));
        }

        return categories;
    }
}
