package com.LMS.LibraryManagementSystem.service;

import com.LMS.LibraryManagementSystem.DTO.CategoryDTO;
import com.LMS.LibraryManagementSystem.model.Category;
import com.LMS.LibraryManagementSystem.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoriesRepository categoriesRepository;

    public List<Category> createCategories(List<CategoryDTO> categoriesList){
        List<Category> categories = new ArrayList<>();
        List<Category> saved = new ArrayList<>();
        try {
            if (!categoriesList.isEmpty()) {
                categories = categoriesList.stream()
                        .map(categoryDTO -> Category.builder()
                                .name(categoryDTO.getName())
                                .build()).toList();
            }
             removeDuplicates(categories);   //Called to remove duplicate categories from the list
             saved = categoriesRepository.saveAll(categories);
        }catch (Exception e){
            System.out.println("Exception occurred : "+e.getMessage());
        }
        return saved;
    }

    public void removeDuplicates(List<Category> categoriesList){
        List<String> list = categoriesList.stream()
                .map(Category::getName).toList();
       List<Category> duplicates = categoriesRepository.findAllByNameIn(list);
       if (!duplicates.isEmpty()){
           categoriesList.removeAll(duplicates);
           System.out.println("Duplicates Removed from the list");
       }else{
         System.out.println("Duplicates not found in the list");
       }
    }
}
