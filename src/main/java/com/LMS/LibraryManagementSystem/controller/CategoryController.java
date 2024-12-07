package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.DTO.CategoryDTO;
import com.LMS.LibraryManagementSystem.model.Category;
import com.LMS.LibraryManagementSystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/addCategories")
    public ResponseEntity<List<Category>> createCategories(@RequestBody List<CategoryDTO> categoryList){
        List<Category> categoriesSaved = new ArrayList<>();
        if (!categoryList.isEmpty()){
             categoriesSaved = categoryService.createCategories(categoryList);
        }
        if (!categoriesSaved.isEmpty()){
            return new ResponseEntity<>(categoriesSaved, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
