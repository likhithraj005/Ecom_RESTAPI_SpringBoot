package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
//    List<Category> getAllCategories();
    CategoryResponse getAllCategories(Integer pageNum, Integer pageSize, String sortBy, String sortOrder);

//    void createCategory(Category category);
    CategoryDTO createCategory(CategoryDTO categoryDTO);

//    String deleteCategory(Long categoryId);
    CategoryDTO deleteCategory(Long categoryId);

//    Category updateCategory(Category category, Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
