package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category,Integer> {

    //find all categories in DB that matches the categories in list
    List<Category> findAllByNameIn(List<String> names);

    Optional<Category> findByName(String name);

}
