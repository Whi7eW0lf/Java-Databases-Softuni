package com.spring.spring.services.impl;

import com.spring.spring.entites.Category;
import com.spring.spring.repositories.CategoryRepository;
import com.spring.spring.services.interfaces.CategoryService;
import com.spring.spring.utill.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.spring.spring.constants.GlobalConstants.CATEGORIES_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() {

        if (this.categoryRepository.count()!=0){
            return;
        }

        String[] fileContent = this.fileUtil.readFileContent(CATEGORIES_PATH);

        Arrays.stream(fileContent).forEach(e->this.categoryRepository.saveAndFlush(new Category(e)));

    }

    @Override
    public Category getCategoryById(long id) {
        return this.categoryRepository.getOne(id);
    }
}
