package com.blog.controllers;

import com.blog.models.dtos.CategoryDto;
import com.blog.serives.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category/")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
         return new ResponseEntity<>(this.categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
         return new ResponseEntity<>(this.categoryService.getCategoryById(categoryId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategory(){
        return new ResponseEntity<>(this.categoryService.getCategories(),HttpStatus.OK);
    }

}
