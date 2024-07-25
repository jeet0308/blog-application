package com.blog.serives;

import com.blog.models.dtos.CategoryDto;
import com.blog.models.entities.Category;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto getCategoryById(Integer categoryId);

    public List<CategoryDto> getCategories();
}
