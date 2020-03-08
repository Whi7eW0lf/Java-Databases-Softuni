package com.spring.spring.services.interfaces;

import com.spring.spring.entites.Category;

public interface CategoryService {
    void seedCategories();

    Category getCategoryById(long id);
}
