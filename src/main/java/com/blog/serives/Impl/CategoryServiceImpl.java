package com.blog.serives.Impl;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.models.dtos.CategoryDto;
import com.blog.models.entities.Category;
import com.blog.payload.MapperService;
import com.blog.repositories.CategoryRepository;
import com.blog.serives.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MapperService mapperService;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        var category = this.mapperService.map(categoryDto, Category.class);
        var save = this.categoryRepository.save(category);
        return this.mapperService.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        var category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not Found"));
        return this.mapperService.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        var categories = this.categoryRepository.findAll();
        return categories.stream().map(category -> this.mapperService.map(category, CategoryDto.class)).collect(Collectors.toList());
    }
}
