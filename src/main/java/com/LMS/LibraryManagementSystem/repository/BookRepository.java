package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByName(String name);

    Optional<List<Book>> findByAuthor(String author);

}
