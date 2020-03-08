package com.spring.services.interfaces;

import com.spring.entites.Category;

import java.util.Set;

public interface CategoryService {

    void seedCategories();

    Set<Category> getRandomCategories();
}
