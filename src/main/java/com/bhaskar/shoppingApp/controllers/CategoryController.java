package com.bhaskar.shoppingApp.controllers;

import com.bhaskar.shoppingApp.dao.CategoryDto;
import com.bhaskar.shoppingApp.enums.Category;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000/")
public class CategoryController {
    @GetMapping("/categories")
    public List<CategoryDto> getCategories(){
        return Arrays.stream(Category.values()).map(category -> new CategoryDto(category.getKey(), category.getName())).collect(Collectors.toList());
    }
}
