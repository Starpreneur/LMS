package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.model.IssuedBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuedBooksRepository extends JpaRepository<IssuedBooks,Long> {

}
